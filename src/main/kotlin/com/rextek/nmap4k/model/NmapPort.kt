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

data class NmapPort(val port: Int,
                    val service: Service,
                    val protocol: Protocol
                    ) {
    enum class Protocol { TCP, UDP, UNKNOWN }
    data class State(val state: State, val reason: String, val reason_ttl: Int) {
        enum class State { OPEN, CLOSED }
    }
    data class Service(val name: String?, val method: String, val conf: Int)
}