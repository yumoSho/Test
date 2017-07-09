package com.glanway.jty.utils;

/**
 * Excel 工具类
 *
 * @author vacoor
 */
public abstract class ExcelUtils {
    public static final byte[] BIFF8_FILE_HEADER = {(byte) 0xD0, (byte) 0xCF, 0x11, (byte) 0xE0};
    public static final byte[] ZIP_FILE_HEADER = {0x50, 0x4b, 0x03, 0x04};

    /**
     * 是否是 excel 2003 的biff8 文件头
     * eg:
     * <p/>
     * PushbackInputStream pis = new PushbackInputStream(new FileInputStream("jxl.out.xls"), 4);
     * byte[] header = new byte[4];
     * pis.read(header);
     * pis.unread(header);
     *
     * @param header 文件的前4个字节
     * @return
     */
    public static boolean isBiff8Header(byte[] header) {
        return header != null &&
                header.length >= BIFF8_FILE_HEADER.length &&
                BIFF8_FILE_HEADER[0] == header[0] &&
                BIFF8_FILE_HEADER[1] == header[1] &&
                BIFF8_FILE_HEADER[2] == header[2] &&
                BIFF8_FILE_HEADER[3] == header[3];
    }

    /**
     * 是否是 excel 2007 的 Open Xml(zip) 文件头
     *
     * @param header 文件的前4个字节
     * @return
     */
    public static boolean isZipHeader(byte[] header) {
        return header != null &&
                header.length >= ZIP_FILE_HEADER.length &&
                ZIP_FILE_HEADER[0] == header[0] &&
                ZIP_FILE_HEADER[1] == header[1] &&
                ZIP_FILE_HEADER[2] == header[2] &&
                ZIP_FILE_HEADER[3] == header[3];
    }


    private ExcelUtils() {
    }
}
