public class StringPool {


    public static void main(String[] args) {
        String a = new String("a");
        String b = new String("a");
        System.out.println(a==b);//因为jvm中string存在常量池，相同的String指向相同的地址
        StringBuffer a1 = new StringBuffer("a");
        StringBuffer b1 = new StringBuffer("a");
        System.out.println(a1==b1);//
        Integer aa =new Integer(1);
        Integer bb =new Integer(1);
        System.out.println(aa==bb);//java基本数据类型自动拆包
    }
}
