import java.util.*;
import javax.swing.*;
import java.awt.*;

public class App
{
    public static void main(String[] args)
    {
        new Page();
    }
}

class Page extends JFrame  //设计界面
{
    private int[][] lotteries=new int[0][];  //声明一个二维数组 用于存储多个注彩票
    private int[] luckyNum=new int[0];
    String[] columnNames={"红球1","红球2","红球3","红球4","红球5","红球6","蓝球"};
    public Page()
    {
        Choices();
    }
    public void Choices()
    {   
        //主界面
        JFrame menu=new JFrame();
        menu.setLayout(null);  //清除布局函数
        menu.setSize(300,400);
		menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		menu.setLayout(new BorderLayout());  //新建BorderLayout布局
        //系统标题
        JPanel panel1=new JPanel(new FlowLayout());
		JLabel jl1=new JLabel("欢迎使用双色球彩票系统");
		jl1.setFont(new Font("微软雅黑", Font.BOLD, 30));  //设置字体、样式、大小
		panel1.add(jl1);
		panel1.setBorder(new javax.swing.border.EmptyBorder(10,10,0,10));
		menu.add(panel1,BorderLayout.NORTH);
        JPanel panel2=new JPanel(new GridLayout(5, 1));
        //按钮1：购买彩票
        JButton jb1=new JButton("购买彩票");
        jb1.addActionListener
        (new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent evt) 
            {
            	JFrame menu1=new JFrame("购买彩票");
                menu1.setSize(300,400);
		        menu1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                menu1.setLayout(new FlowLayout());
                JPanel panel=new JPanel(new GridLayout(3, 1));
                JLabel jl=new JLabel("请输入购买注数");
		        jl.setFont(new Font("微软雅黑", Font.BOLD, 20));
                JTextField text= new JTextField(7);
                text.setDocument(new NumberDocument());
                JButton jb=new JButton("确认");
                jb.setFont(new java.awt.Font("黑体", 1, 20));
                panel.add(jl);
                panel.add(text);
                panel.add(jb);
                panel.setBorder(new javax.swing.border.EmptyBorder(20, 20, 20, 20));
		        menu1.add(panel);
                menu1.pack();
                location(menu1);
		        menu1.setVisible(true);
                jb.addActionListener
                (new java.awt.event.ActionListener(){
                    public void actionPerformed(java.awt.event.ActionEvent evt)
                    {
                        String str=text.getText();
                        if(str.equals(""))
		                {
			                Object[] options={"继续","退出"}; 
			                int res=JOptionPane.showOptionDialog(
                                null,"您还没有输入!","提示",JOptionPane.DEFAULT_OPTION, 
                            JOptionPane.INFORMATION_MESSAGE,null,options,options[0]);
                            if(res==1) menu1.dispose();
		                }
                        else
                        {
                            int num=Integer.parseInt(str);
                            int res=JOptionPane.showConfirmDialog(null,"请支付"+num*2+"元！",
                                            "购买成功",JOptionPane.YES_NO_OPTION);
                            if(res==JOptionPane.YES_OPTION)
                            {
                                lotteries=new int[num][];  //创建一个二维数组 用于存储多个彩票
                                for(int i=0;i<num;i++)  //程序循环生成彩票
                                    lotteries[i]=Lottery.getLottery();
                                menu1.dispose();
                            }
                        }
                    }
                });
            }
        });
        //按钮2：查看彩票
        JButton jb2=new JButton("查看彩票");
        jb2.addActionListener
        (new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent evt) 
            {
                if(lotteries.length==0)
                {
                    Object[] options={"OK"};
                    JOptionPane.showOptionDialog(null,"你未购买彩票！","提示",
                        JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]); 
                }
                else
                {
                    JFrame menu2=new JFrame("彩票号码");
                    menu2.setSize(300,400);
		            menu2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    menu2.setLayout(new FlowLayout());
                    Object[][] cellData=new Object[lotteries.length][7];
                    for(int i=0;i<lotteries.length;i++)
                    {
                        for(int j=0;j<7;j++)
                            cellData[i][j]=String.valueOf(lotteries[i][j]);
                    }
                    JTable table=new JTable(cellData,columnNames);
                    JScrollPane contentPane=new JScrollPane();
                    contentPane.setViewportView(table);
                    menu2.add(contentPane);
                    Quit(menu2);
                }
            }
        });
        //按钮3：查看开奖
        JButton jb3=new JButton("查看开奖");
        jb3.addActionListener
        (new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent evt) 
            {
                if(luckyNum.length==0) luckyNum=Lottery.getLottery();  //生成中奖号码
                Object[][] cellData=new Object[1][7];
                for(int i=0;i<luckyNum.length;i++)
                    cellData[0][i]=String.valueOf(luckyNum[i]);
                JFrame menu3=new JFrame("开奖号码");
                menu3.setSize(300,400);
		        menu3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                menu3.setLayout(new FlowLayout());
                JTable table=new JTable(cellData,columnNames);
                JScrollPane contentPane=new JScrollPane();
                contentPane.setViewportView(table);
                menu3.add(contentPane);
                Quit(menu3);
            }
        });
        //按钮4：查看中奖
        JButton jb4=new JButton("查看中奖");
        jb4.addActionListener
        (new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent evt) 
            {
                if(lotteries.length==0)
                {
                    Object[] options={"OK"};
                    JOptionPane.showOptionDialog(null,"你未购买彩票！","提示",
                        JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]); 
                }
                else if(luckyNum.length==0)
                {
                    Object[] options={"OK"};
                    JOptionPane.showOptionDialog(null,"暂未开奖！","提示",
                        JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]); 
                }
                else
                {
                    JFrame menu4=new JFrame("中奖信息");
                    menu4.setSize(300,400);
		            menu4.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    menu4.setLayout(new FlowLayout());
                    String[] names={"红球1","红球2","红球3","红球4","红球5","红球6","蓝球","中奖情况"};
                    Object[][] cellData=new Object[lotteries.length][8];
                    int i=0,j=0,sum=0;
                    int[] tmp=new int[lotteries.length];
                    for(int[] lottery:lotteries)
                    {
                        tmp[i]=Lottery.isWin(luckyNum,lottery);
                        sum+=tmp[i];
                        for(j=0;j<lottery.length;j++)
                            cellData[i][j]=String.valueOf(lottery[j]);
                        i++;
                    }
                    for(i=0;i<lotteries.length;i++)
                    {
                        if(tmp[i]==5000000) cellData[i][7]="一等奖";
                        else if(tmp[i]==1250000) cellData[i][7]="二等奖";
                        else if(tmp[i]==3000) cellData[i][7]="三等奖";
                        else if(tmp[i]==200) cellData[i][7]="四等奖";
                        else if(tmp[i]==10) cellData[i][7]="五等奖";
                        else if(tmp[i]==5) cellData[i][7]="六等奖";
                        else cellData[i][7]="未中奖";
                    }
		            JLabel jl=new JLabel("共花费"+lotteries.length*2+"元，获奖"+sum+"元！");
		            jl.setFont(new Font("微软雅黑", Font.BOLD, 30));
                    menu4.add(jl);
                    JTable table=new JTable(cellData,names);
                    JScrollPane contentPane=new JScrollPane();
                    contentPane.setViewportView(table);
                    menu4.add(contentPane);
                    Quit(menu4);
                }
            }
        });
        //按钮5：退出系统
        JButton jb5=new JButton("退出系统");
        jb5.addActionListener
        (new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent evt) 
            {
			    int res=JOptionPane.showConfirmDialog(null,"确认退出",
                                "期待与您再会！",JOptionPane.YES_NO_OPTION);
			    if(res==JOptionPane.YES_OPTION) System.exit(0);
            }
        });
        jb1.setFont(new java.awt.Font("黑体", 1, 20));
		jb2.setFont(new java.awt.Font("黑体", 1, 20));
		jb3.setFont(new java.awt.Font("黑体", 1, 20));
		jb4.setFont(new java.awt.Font("黑体", 1, 20));
        jb5.setFont(new java.awt.Font("黑体", 1, 20));
        panel2.add(jb1);
        panel2.add(jb2);
        panel2.add(jb3);
        panel2.add(jb4);
        panel2.add(jb5);
        panel2.setBorder(new javax.swing.border.EmptyBorder(20, 20, 20, 20));
		menu.add(panel2);
        menu.pack();
        location(menu);
		menu.setVisible(true);
    }
    public void location(JFrame menu)  //定位窗口
    {
		int windowHeight=menu.getHeight();  //获取窗体的高
		Toolkit kit=Toolkit.getDefaultToolkit(); //定义工具包
		Dimension screenSize=kit.getScreenSize(); //获取屏幕的尺寸
		int screenWidth=screenSize.width;   //获取屏幕的宽
		int screenHeight=screenSize.height; //获取屏幕的高
		menu.setLocation(screenWidth/2-300,screenHeight/2-windowHeight/2);
	}
    public void Quit(JFrame menu)  //事件：退出提示
    {
        JButton jb=new JButton("退出");
        jb.setFont(new java.awt.Font("黑体", 1, 20));
        menu.add(jb);
        menu.pack();
        location(menu);
        menu.setVisible(true);
        jb.addActionListener
        (new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent evt) 
                {
                    int res=JOptionPane.showConfirmDialog(null,"是否退出",
                                "确认退出",JOptionPane.YES_NO_OPTION);
                    if(res==JOptionPane.YES_OPTION) menu.dispose();
                }
        });
    }
}

class Lottery  //双色球福利彩票系统
{
    public static int[] getLottery()  //生成彩票号码
    {
        int[] lottery=new int[6];
        for(int i=0;i<6;i++)
        {
            int num=(int)(Math.random()*33+1);
            while(isContain(lottery,num))
                num =(int)(Math.random()*33+1);
            lottery[i]=num;
        }
        Arrays.sort(lottery);
        int[] lottery2=new int[7];
        System.arraycopy(lottery,0,lottery2,0,6);
        lottery2[6]=(int)(Math.random()*16+1);
        return lottery2;
    }
    public static boolean isContain(int[] arr,int b)  //确认号码是否吻合
    {
        for(int x:arr)
        {
            if(x==b) return true;
        }
        return false;
    }
    public static int isWin(int[] luckyNum,int[] lottery)  //判断是否中奖
    {
        // 判断前六位有几位相同
        int red=0;
        for(int i=0;i<6;i++) 
            if(luckyNum[i]==lottery[i]) red++;
        int blue=luckyNum[6]==lottery[6]?1:0;
        if(red==6&&blue==1) return 5000000;
        else if(red==6) return 1250000;
        else if(red==5&&blue==1) return 3000;
        else if(red==5||red+blue==5) return 200;
        else if(red==4||red+blue==4) return 10;
        else if(red==0&&blue==1) return 5;
        else return 0;
    }
}

class NumberDocument extends javax.swing.text.PlainDocument //输入限定为数字
{
    public NumberDocument(){}
    public void insertString(int var1, String var2, javax.swing.text.AttributeSet var3) throws javax.swing.text.BadLocationException
    {
        if(this.isNumeric(var2)) super.insertString(var1,var2,var3);
        else Toolkit.getDefaultToolkit().beep();
    }
    private boolean isNumeric(String var1)
    {
        try 
        {
            Long.valueOf(var1);
            return true;
        }
        catch(NumberFormatException var3)
        {
            return false;
        }
    }
}