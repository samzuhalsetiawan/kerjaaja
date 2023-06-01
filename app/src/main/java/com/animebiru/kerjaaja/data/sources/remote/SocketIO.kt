package com.animebiru.kerjaaja.data.sources.remote

import io.socket.client.IO
import io.socket.client.Socket
import java.net.URISyntaxException

object SocketIO {

    val socket: Socket by lazy { IO.socket("http://192.168.100.4:3000") }

}