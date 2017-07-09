package com.glanway.jty.utils;

import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageHelper {

    public static void main() {
        // TODO Auto-generated method stub
        String str = "http://localhost:2906/Default.aspx?id=1&user=2&type=3";
        String filePath = "D:\\Wildlife";
        String fileName = "Wildlife.wmv";
        try {
            URL url = new URL(str);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.addRequestProperty("FileName", fileName);
            connection.setRequestProperty("content-type", "text/html");
            BufferedOutputStream out = new BufferedOutputStream(connection.getOutputStream());

            //读取文件上传到服务器
            File file = new File(filePath);
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] bytes = new byte[1024];
            int numReadByte = 0;
            while ((numReadByte = fileInputStream.read(bytes, 0, 1024)) > 0) {
                out.write(bytes, 0, numReadByte);
            }
            out.flush();
            fileInputStream.close();
            //读取URLConnection的响应
            DataInputStream in = new DataInputStream(connection.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存图片
     *
     * @param img    原图路径
     * @param top    选择框的左边y坐标
     * @param left   选择框的左边x坐标
     * @param width  选择框宽度
     * @param height 选择框高度
     * @return
     * @throws java.io.IOException
     */
    public static boolean saveImage(File img, File destPath, int top, int left, int width, int height, String sformatName) throws IOException {
        BufferedImage bi = (BufferedImage) ImageIO.read(img);
        BufferedImage bi_cropper = bi.getSubimage(left, top, width, height);
        return ImageIO.write(bi_cropper, sformatName, destPath);
    }


    /**
     * @param srcImageFile 源图像地址
     * @param x            目标切片起点x坐标
     * @param y            目标切片起点y坐标
     * @param destWidth    目标切片宽度
     * @param destHeight   目标切片高度
     */
    public static void abscut(File srcImageFile,  int y,int x, int destWidth, int destHeight, String sformatName)
    {
    try {
            Image img;
            ImageFilter cropFilter;
            // 读取源图像
            BufferedImage bi = (BufferedImage)ImageIO.read(srcImageFile);
            int srcWidth = bi.getWidth(); // 源图宽度
            int srcHeight = bi.getHeight(); // 源图高度
           if (srcWidth >= destWidth && srcHeight >= destHeight) {
                Image image = bi.getScaledInstance(srcWidth, srcHeight, Image.SCALE_DEFAULT);

                /*****************************************
                 判断原图的宽高和DIV宽高的大小
                 *  根据图片外层DIV的宽度，选择的起始点则有相对变化
                 ****************************************/
               /* int x1 = x*srcWidth/340;
                int y1 = y*srcHeight/340;
                int w = destWidth;
                int h = destHeight;*/

               int x1 = x*srcWidth/230;
               int y1 = y*srcHeight/270;
               int w = destWidth*srcWidth/400;
               int h = destWidth*srcHeight/270;

               // 四个参数分别为图像起点坐标和宽高
                // 即: CropImageFilter(int x,int y,int width,int height)
                cropFilter = new CropImageFilter(x1, y1, w, h);
                img = Toolkit.getDefaultToolkit().createImage(
                        new FilteredImageSource(image.getSource(), cropFilter));
                BufferedImage tag = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
                Graphics g = tag.getGraphics();
                g.drawImage(img,0, 0, null); // 绘制缩小后的图
                g.dispose();
                // 输出为文件
                ImageIO.write(tag,sformatName, srcImageFile);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

