package test.action;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.GlobalHistogramBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class Test {

	public static void main(String[] args) throws Exception {
		
		// 这是二维码图片  
        //BufferedImage bi = ImageIO.read(new FileInputStream(new File("E:" + File.separator + "qr_code.jpg")));  
  
        // 这是条形码图片  
        //BufferedImage bi = ImageIO.read(new FileInputStream(new File("E:" + File.separator + "ean3.jpg")));  
  
        /*if (bi != null) {  
            Map<DecodeHintType, String> hints = new HashMap<>();  
            hints.put(DecodeHintType.CHARACTER_SET, "utf-8");  
            LuminanceSource source = new BufferedImageLuminanceSource(bi);  
            // 这里还可以是   
            //BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));  
            BinaryBitmap bitmap = new BinaryBitmap(new GlobalHistogramBinarizer(source));  
            Result res = new MultiFormatReader().decode(bitmap, hints);  
            System.out.println(res);  
        }  */
		Test.ean_code();
	}
	
	 private static final int BLACK = 0xFF000000;  
	    private static final int WHITE = 0xFFFFFFFF;  
	  
	    private static BufferedImage toBufferedImage(BitMatrix bm) {  
	        int width = bm.getWidth();  
	        int height = bm.getHeight();  
	        BufferedImage image = new BufferedImage(width, height,  
	                BufferedImage.TYPE_3BYTE_BGR);  
	        for (int i = 0; i < width; i++) {  
	            for (int j = 0; j < height; j++) {  
	                image.setRGB(i, j, bm.get(i, j) ? BLACK : WHITE);  
	            }  
	        }  
	        return image;  
	    }  
	  
	    public static void writeBitMatricToFile(BitMatrix bm, String format,  
	            File file) throws IOException {  
	        BufferedImage image = toBufferedImage(bm);  
	        // 设置logo图标 二维码中间logo设置           
	        // 注意条形码一般中间是不会有图片的、若生成条形码 、下面2行需注释掉  
	         image = Test.LogoMatrix(image);  
	        try {  
	            if (!ImageIO.write(image, format, file)) {  
	                throw new RuntimeException("Can not write an image to file"  
	                        + file);  
	            }  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        }  
	    }  
	  
	    public static void writeToStream(BitMatrix matrix, String format,  
	            OutputStream stream) throws IOException {  
	        BufferedImage image = toBufferedImage(matrix);  
	        // 设置logo图标 二维码中间logo设置  
	        // 注意条形码一般中间是不会有图片的、若生成条形码 、下面2行需注释掉  
	        // LogoConfig logoConfig = new LogoConfig();  
	        // image = logoConfig.LogoMatrix(image);  
	        if (!ImageIO.write(image, format, stream)) {  
	            throw new IOException("Could not write an image of format "  
	                    + format);  
	        }  
	    } 
	    
	    /** 
	     * 设置 logo 
	     *  
	     * @param matrixImage 源二维码图片 
	     * @return 返回带有logo的二维码图片 
	     * @throws IOException 
	     * @author Administrator sangwenhao 
	     */  
	    public static BufferedImage LogoMatrix(BufferedImage matrixImage)  
	            throws IOException {  
	        /** 
	         * 读取二维码图片，并构建绘图对象 
	         */  
	        Graphics2D g2 = matrixImage.createGraphics();  
	  
	        int matrixWidth = matrixImage.getWidth();  
	        int matrixHeigh = matrixImage.getHeight();  
	  
	        /** 
	         * 读取Logo图片 
	         */  
	        BufferedImage logo = ImageIO.read(new File("E:\\luvl.jpg"));  
	  
	        // 开始绘制图片  
	        g2.drawImage(logo, matrixWidth / 5 * 2, matrixHeigh / 5 * 2,  
	                matrixWidth / 5, matrixHeigh / 5, null);// 绘制  
	        BasicStroke stroke = new BasicStroke(5, BasicStroke.CAP_ROUND,  
	                BasicStroke.JOIN_ROUND);  
	        g2.setStroke(stroke);// 设置笔画对象  
	        // 指定弧度的圆角矩形  
	        RoundRectangle2D.Float round = new RoundRectangle2D.Float(  
	                matrixWidth / 5 * 2, matrixHeigh / 5 * 2, matrixWidth / 5,  
	                matrixHeigh / 5, 20, 20);  
	        g2.setColor(Color.white);  
	        g2.draw(round);// 绘制圆弧矩形  
	  
	        // 设置logo 有一道灰色边框  
	        BasicStroke stroke2 = new BasicStroke(1, BasicStroke.CAP_ROUND,  
	                BasicStroke.JOIN_ROUND);  
	        g2.setStroke(stroke2);// 设置笔画对象  
	        RoundRectangle2D.Float round2 = new RoundRectangle2D.Float(  
	                matrixWidth / 5 * 2 + 2, matrixHeigh / 5 * 2 + 2,  
	                matrixWidth / 5 - 4, matrixHeigh / 5 - 4, 20, 20);  
	        g2.setColor(new Color(128, 128, 128));  
	        g2.draw(round2);// 绘制圆弧矩形  
	  
	        g2.dispose();  
	        matrixImage.flush();  
	        return matrixImage;  
	    }  
	
	/**
	 *二维码
	 * @throws IOException 
	*/
	public static void qr_code() throws WriterException, IOException {
		int width = 300;
		int height = 300;
		String text = "https://www.baidu.com/";
		String format = "png";
		HashMap<EncodeHintType, Object> hints = new HashMap<>();
		// 指定纠错等级,纠错级别（L 7%、M 15%、Q 25%、H 30%）
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		// 内容所使用字符集编码
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		// 二维码的格式是BarcodeFormat.QR_CODE
		BitMatrix bm = new MultiFormatWriter().encode(text,
				BarcodeFormat.QR_CODE, width, height, hints);
		// 生成二维码图片
		// File out = new File("qr_code.png"); //默认项目根目录里
		File out = new File("E:" + File.separator + "qr_code.png"); // 指定输出路径  File.separator解决跨平台
		Test.writeBitMatricToFile(bm, format, out);
	}

	/**
	 *条形码
	 * @throws IOException 
	*/
	public static void ean_code() throws WriterException, IOException {
		int width = 200;
		int height = 100;
		String text = "6923450657711";
		String format = "png";
		HashMap<EncodeHintType, String> hints = new HashMap<>();
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		// 条形码的格式是 BarcodeFormat.EAN_13
		BitMatrix bm = new MultiFormatWriter().encode(text,
				BarcodeFormat.EAN_13, width, height, hints);
		// 生成条形码图片
		// File out = new File("ean3.png");
		File out = new File("E:" + File.separator + "ean3.png");// 指定输出路径 // File.separator解决跨平台
		Test.writeBitMatricToFile(bm, format, out);
	}
	
}
