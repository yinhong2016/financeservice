/**
 * Copyright © 2016 Seven Color. All rights reserved.
 *
 * @Description: 图片处理工具
 * @author: yinhong
 * @date: 2016年11月24日 下午1:24:03
 * @version: V1.0
 */
package com.sevencolor.comm.util;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.MemoryCacheImageInputStream;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import magick.ImageInfo;
import magick.MagickException;
import magick.MagickImage;

/**
 * @Description: 图片处理工具
 */
/**
 * @Description: TODO
 */
public class ImageUtil {

  private static Logger logger = LoggerFactory.getLogger(ImageUtil.class);

  public static final String W_H_SPLITER = "x";
  public static final String COMPRESSION_IDENTITY = "comp";
  public static final String TMP_SUFFIX = ".tmp.jpg";

  /**
   * @Description: 指定路径，按照指定的像素缩放图片
   * @return: void
   */
  public static void scaleImg(String fromImg, String toImg, int widthPx, int heightPx)
      throws MagickException {
    MagickImage magickImg = null;
    try {
      ImageInfo fromImgInfo = new ImageInfo(fromImg);
      MagickImage mgkImage = new MagickImage(fromImgInfo);

      magickImg = mgkImage.scaleImage(widthPx, heightPx);
      magickImg.setFileName(toImg);
      magickImg.writeImage(fromImgInfo);
    } finally {
      if (magickImg != null) {
        magickImg.destroyImages();
      }
    }
  }

  /**
   * @Description: 按指定的宽度或高度，计算出压缩比后进行缩放。
   * @return: String 新图片名称
   */
  public static String ratioScaleImg(String fromImg, int width, int height) {
    MagickImage magickImg = null;
    String filename = FilenameUtils.getName(fromImg);
    try {
      ImageInfo fromImgInfo = new ImageInfo(fromImg);
      magickImg = new MagickImage(fromImgInfo);

      Dimension dim = magickImg.getDimension();
      int accessSize = width;
      double widthPx = dim.getWidth();
      double heightPx = dim.getHeight();
      if (width > 0) {
        height = (int) (heightPx * (width / widthPx));
      } else if (height > 0) {
        accessSize = height;
        width = (int) (widthPx * (height / heightPx));
      } else {
        logger.warn("one of width or height should be over zero.",
            "fromImg:" + fromImg + ",width:" + width + ",height:" + height);
        return filename;
      }

      String ext = FilenameUtils.getExtension(filename);
      filename = filename + accessSize + "." + ext;

      String baseDir = FilenameUtils.getFullPath(fromImg);

      scaleImg(fromImg, baseDir + filename, width, height);

    } catch (Exception e) {
      logger.error("fromImg:" + fromImg + ",width:" + width + ",height:" + height, e);

      return FilenameUtils.getName(fromImg);
    }

    return filename;
  }

  /**
   * @Description: 按照指定的像素缩放图片
   * @return: String 新图片名称
   */
  public static String scaleImg(String fromImg, int widthPx, int heightPx) {
    final String size = widthPx + W_H_SPLITER + heightPx;
    String filename = FilenameUtils.getName(fromImg);
    try {
      String baseDir = FilenameUtils.getFullPath(fromImg);

      String ext = FilenameUtils.getExtension(filename);
      filename = filename + size + "." + ext;

      String filePath = baseDir + filename;

      scaleImg(fromImg, filePath, widthPx, heightPx);

    } catch (MagickException e) {
      logger.error("fromImg:" + fromImg + ",widthPx:" + widthPx + ",heightPx:" + heightPx, e);

      return FilenameUtils.getName(fromImg);
    }

    return filename; // 压缩成功，则将resultDir+"/"+文件名返回
  }


  /**
   * @Description: 按照指定质量（0-100）压缩图片
   * @return: void
   */
  public static void compressionImg(String fromImg, String toImg, int quality)
      throws MagickException {
    MagickImage magickImg = null;
    try {
      ImageInfo fromImgInfo = new ImageInfo(fromImg);
      fromImgInfo.setQuality(quality);
      magickImg = new MagickImage(fromImgInfo);
      magickImg.setFileName(toImg);
      magickImg.writeImage(fromImgInfo);
    } finally {
      if (magickImg != null) {
        magickImg.destroyImages();
      }
    }
  }

  /**
   * @Description: 按照指定质量（0-100）压缩图片
   * @return: String 新图片名称
   */
  public static String compressionImg(String fromImg, int quality) {
    String filename = FilenameUtils.getName(fromImg);
    try {
      String baseDir = FilenameUtils.getFullPath(fromImg);
      String ext = FilenameUtils.getExtension(filename);
      filename = filename + COMPRESSION_IDENTITY + "." + ext;
      String filePath = baseDir + filename;
      compressionImg(fromImg, filePath, quality);
    } catch (MagickException e) {
      logger.error("fromImg:" + fromImg + ",quality:" + quality, e);
      return FilenameUtils.getName(fromImg);
    }

    return filename; // 压缩成功，则将resultDir+"/"+文件名返回
  }

  /**
   * 对图片进行裁切
   * 
   * @param fromImg 原图路径
   * @param toImg 被裁切后的图片路径
   * @param x 相对图片左上角的x坐标
   * @param y 相对图片左上角的y坐标
   * @param w 裁切宽度
   * @param h 裁切高度
   * @throws MagickException 压缩不成功，可能会跑出该异常
   */
  public static void cutImg(String fromImg, String toImg, int x, int y, int w, int h)
      throws MagickException {
    MagickImage magickImg = null;
    Rectangle rect = null;
    try {
      ImageInfo fromImgInfo = new ImageInfo(fromImg);
      magickImg = new MagickImage(fromImgInfo);
      rect = new Rectangle(x, y, w, h);
      magickImg = magickImg.cropImage(rect);
      magickImg.setFileName(toImg);
      magickImg.writeImage(fromImgInfo);
    } finally {
      if (magickImg != null) {
        magickImg.destroyImages();
      }
    }
  }

  /**
   * 对图片进行裁切
   * 
   * @param fromImg 原图路径
   * @param x 相对图片左上角的x坐标
   * @param y 相对图片左上角的y坐标
   * @param w 裁切宽度
   * @param h 裁切高度
   * @return 如果压缩成功，则返回压缩后的图片名称；如果压缩失败，则返回原图的文件名
   */
  public static String cutImg(String fromImg, int x, int y, int w, int h) {
    final String size = w + W_H_SPLITER + h;
    String filename = FilenameUtils.getName(fromImg);
    try {
      String baseDir = FilenameUtils.getFullPath(fromImg);
      String ext = FilenameUtils.getExtension(filename);
      filename = filename + size + "." + ext;
      String filePath = baseDir + filename;
      cutImg(fromImg, filePath, x, y, w, h);
    } catch (MagickException e) {
      logger.error("fromImg:" + fromImg + ",x:" + x + ",y:" + y + ",w:" + w + ",h:" + h, e);
      return FilenameUtils.getName(fromImg);
    }

    return filename; // 压缩成功，则将resultDir+"/"+文件名返回
  }

  /**
   * 对图片进行裁切
   * 
   * @param fromImg 原图路径
   * @param x 相对图片左上角的x坐标
   * @param y 相对图片左上角的y坐标
   * @param leftx 相对图片左上角的另外一个x坐标
   * @param topy 相对图片左上角的另外一个y坐标
   * @return 如果压缩成功，则返回压缩后的图片名称；如果压缩失败，则返回原图的文件名
   */
  public static String cutImgByPx(String fromImg, double x, double y, double leftx, double topy) {
    int width = (int) Math.abs(leftx - x);
    int height = (int) Math.abs(topy - y);

    return cutImg(fromImg, (int) x, (int) y, width, height);
  }

  /**
   * 对图片进行裁切
   * 
   * @param fromImg 原图路径
   * @param toImg 被裁切后的图片路径
   * @param x 相对图片左上角的x坐标
   * @param y 相对图片左上角的y坐标
   * @param leftx 相对图片左上角的另外一个x坐标
   * @param topy 相对图片左上角的另外一个y坐标
   * @return 如果压缩成功，则返回压缩后的图片名称；如果压缩失败，则返回原图的文件名
   */
  public static boolean cutImgByPx(String fromImg, String toImg, double x, double y, double leftx,
      double topy) {
    int width = (int) Math.abs(leftx - x);
    int height = (int) Math.abs(topy - y);

    try {
      cutImg(fromImg, toImg, (int) x, (int) y, width, height);
    } catch (MagickException e) {
      logger.error("fromImg:" + fromImg + ",toImg:" + toImg, e);
    }

    return new File(toImg).exists();
  }

  /**
   * 对图片进行裁切
   * 
   * @param fromImg 原图路径
   * @param width 压缩后的宽度
   * @param height 压缩后的高度
   * @param quality 压缩质量，从0到100之间的数字
   * @return 如果压缩成功，则返回压缩后的图片名称；如果压缩失败，则返回原图的文件名
   */
  public static String convertUploadImg(String fromImg, int width, int height, int quality) {
    MagickImage magickImg = null;
    final String size = width + W_H_SPLITER + height;
    String filename = FilenameUtils.getName(fromImg);
    try {
      ImageInfo fromImgInfo = new ImageInfo(fromImg);
      fromImgInfo.setQuality(quality);
      magickImg = new MagickImage(fromImgInfo);

      magickImg = magickImg.scaleImage(width, height);

      String baseDir = FilenameUtils.getFullPath(fromImg);

      checkDirs(baseDir);

      String ext = FilenameUtils.getExtension(filename);
      filename = filename + size + "." + ext;

      String filePath = baseDir + filename;
      magickImg.setFileName(filePath);
      magickImg.writeImage(fromImgInfo);
    } catch (MagickException e) {
      logger.error(
          "fromImg:" + fromImg + ",width:" + width + ",height:" + height + ",quality:" + quality,
          e);

      return FilenameUtils.getName(fromImg);
    } finally {
      if (magickImg != null) {
        magickImg.destroyImages();
      }
    }

    return filename;
  }

  /**
   * 
   * @param dir 被检测的目录路径 如果目录dir存在，则直接返回；如果不存在，则创建该路径
   */
  private static void checkDirs(String dir) {
    File file = new File(dir);
    if (!file.exists()) {
      file.mkdirs();
    }
  }

  /**
   * 
   * <Description>获取图片的尺寸信息</Description>
   * 
   * @param fromImg 图片路径
   * @return 获取包含该图片宽高的Dimension对象
   * @throws MagickException 获取尺寸对象失败，有可能会抛出该异常
   */
  public static Dimension getDimension(String fromImg) throws MagickException {
    ImageInfo fromImgInfo = new ImageInfo(fromImg);
    return new MagickImage(fromImgInfo).getDimension();
  }

  /**
   * 判断是否为图片
   * 
   * @param mapObj 图片的字节码数组
   * @return 如果该字节码数组确实为图片格式，则返回true；否则返回false
   */
  public static boolean isImageType(byte[] mapObj) {
    boolean ret = false;
    ByteArrayInputStream bais = null;
    MemoryCacheImageInputStream mcis = null;
    try {
      bais = new ByteArrayInputStream(mapObj);
      mcis = new MemoryCacheImageInputStream(bais);
      Iterator<ImageReader> itr = ImageIO.getImageReaders(mcis);

      while (itr.hasNext()) {
        ImageReader reader = (ImageReader) itr.next();

        String imageName = reader.getClass().getSimpleName();

        if (imageName != null
            && ("GIFImageReader".equals(imageName) || "JPEGImageReader".equals(imageName)
                || "PNGImageReader".equals(imageName) || "BMPImageReader".equals(imageName))) {
          ret = true;
        }
      }
    } finally {
      // 关闭流
      if (mcis != null) {
        try {
          mcis.close();
        } catch (IOException e) {
          logger.error("close MemoryCacheImageInputStream failed.", e);
        }
      }

      if (bais != null) {
        try {
          bais.close();
        } catch (IOException e) {
          logger.error("close ByteArrayInputStream failed.", e);
        }
      }
    }

    return ret;
  }

  /**
   * 
   * @param file 文件路径
   * @return 如果该文件确实为图片格式，则返回true；否则返回false
   * @throws IOException
   */
  public static boolean isImageType(String fileName) {
    return isImageType(new File(fileName));
  }

  /**
   * 
   * @param file 文件对象
   * @return 如果该文件确实为图片格式，则返回true；否则返回false
   * @throws FileNotFoundException
   * @throws IOException
   */
  public static boolean isImageType(File file) {
    String filePath = file == null ? "" : file.getAbsolutePath();
    try {
      return isImageType(new FileInputStream(file));
    } catch (FileNotFoundException e) {
      logger.error(filePath + " is not existed.", e);
    }
    return false;
  }

  /**
   * 
   * @param input 文件流
   * @return 如果该文件流确实为图片格式，则返回true；否则返回false
   * @throws IOException
   */
  public static boolean isImageType(InputStream input) {
    try {
      return isImageType(IOUtils.toByteArray(input));
    } catch (IOException e) {
      logger.error("inputstream transform into byte array failed.", e);
    }
    return false;
  }

}
