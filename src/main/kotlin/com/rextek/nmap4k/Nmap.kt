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


package com.rextek.nmap4k

import com.rextek.nmap4k.model.NmapHost
import org.w3c.dom.Document
import org.w3c.dom.Element
import java.io.File
import java.net.InetAddress
import javax.xml.parsers.DocumentBuilderFactory

object Nmap {

    /**
     * Perform a scan using nmap according to the given command.
     * This method suspends.
     * @param command a string command to pass to nmap, eg "-sN 192.168.1.234"
     */
    fun scan(command: String, deleteFileOnDone: Boolean = true): Map<InetAddress, NmapHost> {
        Runtime.getRuntime().exec("nmap -oX temp.xml $command").waitFor()
        val file = File("temp.xml")
        val document = DocumentBuilderFactory
            .newDefaultInstance()
            .newDocumentBuilder()
            .parse(file)
        if (deleteFileOnDone) file.delete()

        return parseDocument(document)
    }

    fun parseDocument(document: Document) = buildMap {
        val hostElements = document.getElementsByTagName("host")

        for (i in 0 until hostElements.length) {
            val host = NmapHost.fromXML(hostElements.item(i) as Element)
            for (address in host.addresses) {
                put(address, host)
            }
        }
    }
}