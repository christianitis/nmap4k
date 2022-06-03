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


import com.rextek.nmap4k.Nmap
import com.rextek.nmap4k.model.NmapHost
import org.w3c.dom.Element
import java.io.File
import java.net.InetAddress
import javax.xml.parsers.DocumentBuilderFactory
import kotlin.test.Test

class NMapTests {

    @Test
    fun scannerTest() {
        println(Nmap.scan("-T5 -p 80,443 192.168.1.0/30"))
    }

}