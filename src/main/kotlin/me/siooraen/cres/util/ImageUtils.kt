package me.siooraen.cres.util

import java.awt.Image.SCALE_AREA_AVERAGING
import java.awt.image.BufferedImage
import java.awt.image.BufferedImage.TYPE_INT_ARGB

fun resizeImage(originalImage: BufferedImage, targetWidth: Int, targetHeight: Int): BufferedImage {
    val resultingImage = originalImage.getScaledInstance(targetWidth, targetHeight, SCALE_AREA_AVERAGING)
    val newImage = BufferedImage(targetWidth, targetHeight, TYPE_INT_ARGB)
    newImage.graphics.drawImage(resultingImage, 0, 0, null)
    return newImage
}