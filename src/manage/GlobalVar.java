/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manage;

import java.awt.Dimension;
import java.awt.Toolkit;

/**
 *
 * @author khanchaiwongsit
 */
public class GlobalVar {
    public static String userName= "";
        public static String passWord = "";
        public static String name ="";
        public static String memberType= "";
        public static Boolean status=false;

        public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        public static int screenWidth = (int) screenSize.getWidth();
        public static int screenHeight = (int) screenSize.getHeight();
        public static int screenCenterX = (int) screenWidth/2;
        public static int screenCenterY = (int) screenHeight/2;

        public static String appData = System.getenv("APPDATA");
        public static String iconLogo = appData + "\\icon\\book32.png";
}
