package com.animebiru.kerjaaja.data.sources.remote

import io.socket.client.IO
import io.socket.client.Socket
import java.net.URISyntaxException

object SocketIO {

    val socket: Socket by lazy {
        IO.socket("https://kerjaaja-backend-production.up.railway.app/chat-feature")
    }

}