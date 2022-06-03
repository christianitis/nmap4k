nmap4k: A wrapper around nmap written in Kotlin.
-----
#### Copyright 2022 Christian Hollinger. See the COPYING and COPYING.LESSER files for licensing details.

Nmap4K (nmap for Kotlin) is a simple wrapper around NMap. It is written in Kotlin for the JVM,
meaning it is compatible with all JVM languages (including Java).
It works by analyzing the XML output created by <code>nmap -oX</code> and creating objects
representing devices on your network from each XML element. Keep in mind that the instances are static
and may not accurately represent the state of your network should things change, so you may need to
re-construct them periodically to maintain their accuracy.

Example:
-------
<code>
Nmap.scan("-p 80,443 192.168.1.0/30")
</code>
Will return a map of <code>InetAddress</code>es to <code>NmapHost</code>s.