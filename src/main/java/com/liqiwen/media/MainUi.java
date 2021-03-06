package com.liqiwen.media;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.liqiwen.media.listener.ChooseFileDialogListener;
import com.liqiwen.media.listener.ExtractMetadataListener;
import com.liqiwen.media.listener.ProcessListener;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.Arrays;

/**
 * @author liqiwen
 */
public class MainUi {


    private final Font font = new Font(null, Font.PLAIN, 16);

    public static void main(String[] args) {
        //创建事件调度线程
        SwingUtilities.invokeLater(() -> {
            FlatDarculaLaf.install();
            MainUi mainUi = new MainUi();
            mainUi.createMainUi();
        });
    }


    /**
     * create main ui
     */
    public void createMainUi() {
        JFrame mainFrame = new JFrame("MediaTool");
        mainFrame.setSize(900, 600);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //创建选项卡面板
        final JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.add("视频压制", createVideoProcessPanel());
        tabbedPane.add("图片处理", createImageProcessPanel());
        tabbedPane.add("资源下载", createResourceDownloadPanel());

        tabbedPane.addChangeListener(e -> System.out.println("current selected " + tabbedPane.getSelectedIndex()));

        tabbedPane.setSelectedIndex(0);

        //显示窗口
        mainFrame.setContentPane(tabbedPane);
        mainFrame.setVisible(true);

    }

    /**
     * 创建视频处理面板
     * @return JComponent
     */
    private JComponent createVideoProcessPanel() {
        //创建主面板
        JPanel mainPanel = new JPanel(null);

        JSplitPane videoProcessPanel = new JSplitPane();
        videoProcessPanel.setOrientation(JSplitPane.VERTICAL_SPLIT);

        //top
        JSplitPane splitPane = new JSplitPane();
        splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);

        // top -> left
        splitPane.setLeftComponent(createVideoProcessLeftPanel());


        // top -> right
        splitPane.setRightComponent(createVideoProcessRightPanel());

        videoProcessPanel.setTopComponent(splitPane);


        JSplitPane bottomSplitPane = new JSplitPane();




        //=======================进程完整指令输出 ===========================
        JPanel jPanel = new JPanel(new GridLayout(2, 1));
        JLabel jLabel = new JLabel("完整指令");
        jLabel.setFont(font);
        jPanel.add(jLabel);

        //bottom
        // 创建文本组件区域
        JTextArea outputLogTextArea = new JTextArea();
        //设置自动换行
        outputLogTextArea.setLineWrap(true);
        //设置字体
        outputLogTextArea.setFont(font);
        JScrollPane jScrollPane = new JScrollPane(outputLogTextArea,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jPanel.add(jScrollPane);

        bottomSplitPane.setTopComponent(jPanel);

        //=======================进程完整指令输出结束 ===========================


        //=======================进程执行结果输出 ===========================
        JPanel processOutputResultPanel = new JPanel(new GridLayout(2, 1));
        JLabel processOutputLabel = new JLabel("输出结果");
        processOutputLabel.setFont(font);
        processOutputResultPanel.add(processOutputLabel);
        // 创建文本组件区域
        JTextArea processOutputResult = new JTextArea();
        //设置自动换行
        outputLogTextArea.setLineWrap(true);
        //设置字体
        outputLogTextArea.setFont(font);
        JScrollPane processOutputResultPane = new JScrollPane(processOutputResult,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        processOutputResultPanel.add(processOutputResultPane);
        bottomSplitPane.setBottomComponent(processOutputResultPanel);
        //=======================进程执行结果输出结束 ===========================



        videoProcessPanel.setBottomComponent(bottomSplitPane);


        //left


        return videoProcessPanel;

//        JTextField jTextField = new JTextField();
//        jTextField.setBounds(0, 0, 200, 50);
//
//
//        JButton chooseFileBtn = new JButton("选择文件");
//        chooseFileBtn.setBounds(100, 100, 200, 40);
//        chooseFileBtn.addActionListener(new ChooseFileDialogListener(mainPanel));
//
//
//
//
//
//        JButton jButton = new JButton("获取元数据");
//        jButton.setBounds(220, 0, 50, 50);
//
//        JButton startProcessBtn = new JButton("Start Process");
//        startProcessBtn.setBounds(100, 200, 200, 40);
//
//
//        mainPanel.add(jTextField);
//        mainPanel.add(jButton);
//        mainPanel.add(chooseFileBtn);
//        mainPanel.add(startProcessBtn);
//
//
//
//
//        startProcessBtn.addActionListener(new ProcessListener(mainPanel));
//
//        jButton.addActionListener(new ExtractMetadataListener(jTextField));
//        return mainPanel;
    }

    private JComponent createVideoProcessRightPanel() {
        JPanel jPanel = new JPanel();
        JList<String> list = new JList<>();

        list.setPreferredSize(new Dimension(300, 900));

        //选择模式
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int selectedIndex = list.getSelectedIndex();
                ListModel<String> model = list.getModel();
                String elementAt = model.getElementAt(selectedIndex);
                System.out.println("selected + :" + elementAt);
            }
        });

        //默认选中
        list.setSelectedIndex(1);


        list.setListData(new String[]{"不使用预设","H264转码", "H265转码", "视频截图", "格式转换", "音频分离", "动态图片", "添加水印", "视频配速"});
        jPanel.add(list);
        return jPanel;
    }

    private JComponent createVideoProcessLeftPanel() {
        JPanel jPanel = new JPanel();

        //第一行
        Box hBox01 = Box.createHorizontalBox();
        JLabel label = new JLabel("input");
        hBox01.add(label);

        JTextField jTextField = new JTextField();
        jTextField.setColumns(30);
        hBox01.add(jTextField);

        JButton jButton = new JButton("file");
        hBox01.add(jButton);

        //第二行
        Box hBox100 = Box.createHorizontalBox();
        JButton generateBtn = new JButton("generate");
        hBox100.add(generateBtn);

        Box vBox = Box.createVerticalBox();
        vBox.add(hBox01);
        vBox.add(hBox100);

        jPanel.add(vBox);



        return jPanel;
    }


    /**
     * 创建图片处理的面板
     * @return JComponent
     */
    private JComponent createImageProcessPanel() {
        JPanel imagePanel = new JPanel(null);
        JButton jButton = new JButton("button");
        jButton.setBounds(100, 100, 200, 50);
        imagePanel.add(jButton);
        return imagePanel;
    }

    /**
     * 下载面板
     * @return JComponent
     */
    private JComponent createResourceDownloadPanel() {
        JPanel downloadPanel = new JPanel(null);
        JButton jButton = new JButton("download");
        jButton.setBounds(100, 100, 200, 50);
        downloadPanel.add(jButton);
        return downloadPanel;
    }
}
