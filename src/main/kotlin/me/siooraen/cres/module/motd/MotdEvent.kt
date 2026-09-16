package me.siooraen.cres.module.motd

import me.siooraen.cres.module.motd.Motd.motdConf
import me.siooraen.cres.util.resizeImage
import org.bukkit.Bukkit.loadServerIcon
import org.bukkit.event.server.ServerListPingEvent
import taboolib.common.platform.event.SubscribeEvent
import taboolib.common.platform.function.getDataFolder
import taboolib.module.chat.colored
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

/**
 * @author Siooraen
 * @since 2022/2/5 12:19
 */
object MotdEvent {

    @SubscribeEvent
    fun e(e: ServerListPingEvent) {
        e.motd = motdConf.getString("description")!!.colored()
        e.maxPlayers = 0
        val image: BufferedImage? = ImageIO.read(File("${getDataFolder()}//module//motd//icon.png"))
        image?.let { e.setServerIcon(loadServerIcon(resizeImage(image, 64, 64))) }
    }
}