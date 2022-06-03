/*
 * Copyright 2022 Christian Hollinger
 *
 * This file is part of nmap4k.
 *
 * nmap4k is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * nmap4k is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with nmap4k. If not, see <https://www.gnu.org/licenses/>.
 */



package com.rextek.nmap4k.model

import org.w3c.dom.Element
import java.net.InetAddress

data class NmapHost(val addresses: Set<InetAddress>, val mac: String?, val ports: Map<Int, NmapPort>, val status: Status) {
    data class Status(val state: State, val reason: String, val reason_ttl: Int) {
        enum class State { UP, FILTERED }
    }
    companion object {
        fun fromXML(element: Element): NmapHost {

            var mac: String? = null
            val addresses = buildSet {
                element.getElementsByTagName("address").let { elements ->
                    for (addressIndex in 0 until elements.length) elements.item(addressIndex).let { addressElement ->
                        val addrtype = addressElement.attributes.getNamedItem("addrtype").nodeValue
                        val addr = addressElement.attributes.getNamedItem("addr").nodeValue
                        if (addrtype == "mac") mac = addr else add(InetAddress.getByName(addr))
                    }
                }
            }

            val status = element.getElementsByTagName("status").item(0).attributes.let {
                Status(
                    state = Status.State.valueOf(it.getNamedItem("state").nodeValue.uppercase()),
                    reason = it.getNamedItem("reason").nodeValue,
                    reason_ttl = it.getNamedItem("reason_ttl").nodeValue.toInt()
                )
            }

            val ports = buildMap {
                val elements = element.getElementsByTagName("port")
                for (i in 0 until elements.length) {
                    elements.item(i).let { portElement ->
                        portElement.attributes.apply {
                            put(getNamedItem("portid").nodeValue.toInt(), NmapPort(
                                port = getNamedItem("portid").nodeValue.toInt(),
                                protocol = NmapPort.Protocol.valueOf(getNamedItem("protocol").nodeValue.uppercase()),
                                service = portElement.lastChild.attributes.let {
                                            NmapPort.Service(
                                                name = it.getNamedItem("name")?.nodeValue,
                                                method = it.getNamedItem("method").nodeValue,
                                                conf = it.getNamedItem("conf").nodeValue.toInt()
                                            )
                                    }
                                )
                            )
                        }
                    }
                }
            }
            return NmapHost(
                addresses = addresses,
                mac = mac,
                ports = ports,
                status = status
            )
        }
    }
}