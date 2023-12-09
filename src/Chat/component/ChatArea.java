package Chat.component;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javaswingdev.FontAwesome;
import javaswingdev.FontAwesomeIcon;
import javaswingdev.GoogleMaterialDesignIcon;
import javaswingdev.GoogleMaterialIcon;
import javaswingdev.GradientType;
import javax.swing.JLabel;
import Chat.animation.AnimationFloatingButton;
import Chat.animation.AnimationScroll;
import Chat.model.ModelMessage;
import Chat.swing.Button;
import Chat.swing.RoundPanel;
import Chat.swing.TextField;
import Chat.swing.scroll.ScrollBar;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;
import Chat.swing.ChatEvent;
import Chat.swing.ImageAvatar;
import java.awt.Font;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class ChatArea extends JPanel {

    private AnimationScroll animationScroll;
    private AnimationFloatingButton animationFloatingButton;
    private List<ChatEvent> events = new ArrayList<>();

    public void addChatEvent(ChatEvent event) {
        events.add(event);
    }

    public ChatArea() {
        init();
        initAnimator();
    }

    private void init() {
        setOpaque(false);
        layout = new MigLayout("fill, wrap, inset 0", "[fill]", "[fill,40!][fill, 100%][shrink 0,::30%]");
        header = createHeader();
        body = createBody();
        bottom = createBottom();
        layeredPane = createLayeredPane();
        scrollBody = createScroll();
        scrollBody.setViewportView(body);
        scrollBody.setVerticalScrollBar(new ScrollBar());
        scrollBody.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollBody.getViewport().setOpaque(false);
        scrollBody.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
            private int oldValues;

            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                int value = scrollBody.getVerticalScrollBar().getValue();
                int extent = scrollBody.getVerticalScrollBar().getModel().getExtent();
                if ((value + extent) >= scrollBody.getVerticalScrollBar().getMaximum() - 300) {
                    animationFloatingButton.hide();
                } else if (oldValues <= e.getValue()) {
                    if (!animationScroll.isRunning()) {
                        animationFloatingButton.show();
                    }
                }

            }
        });
        floatingButton = createFloatingButton();
        layeredPane.setLayer(floatingButton, JLayeredPane.POPUP_LAYER);
        layeredPane.add(floatingButton, "pos 100%-50 100%,h 40,w 40");
        layeredPane.add(scrollBody);
        setLayout(layout);
        add(header);
        add(layeredPane);
        add(bottom);
    }

    private void initAnimator() {
        animationScroll = new AnimationScroll(body);
        animationFloatingButton = new AnimationFloatingButton(layoutLayered, floatingButton);
    }

    private JPanel createHeader() {
        RoundPanel panel = new RoundPanel();
        panel.setLayout(new MigLayout("fill, inset 2", "[fill,40!]2[fill,40!]2[fill]2[fill,40!]2[fill,40!]"));
        panel.setBackground(new Color(255, 255, 255, 20));
        ImageAvatar avt = new ImageAvatar();
        Icon icon = new ImageIcon(getClass().getResource("/Chat/component/p2.png"));
        avt.setImage(icon);
        avt.setBorderSize(2);
        avt.setBorderSpace(0);
        labelTitle = new JLabel();
        labelTitle.setFont(new Font("SansSerif", Font.ITALIC | Font.BOLD, 15));
        labelTitle.setBorder(new EmptyBorder(2, 10, 2, 2));
        labelTitle.setForeground(new Color(240, 240, 240));
        GoogleMaterialIcon iconBack = new GoogleMaterialIcon(GoogleMaterialDesignIcon.CHEVRON_LEFT, GradientType.VERTICAL, new Color(210, 210, 210), new Color(255, 255, 255), 28);
        GoogleMaterialIcon iconVideoCall = new GoogleMaterialIcon(GoogleMaterialDesignIcon.VIDEO_CALL, GradientType.VERTICAL, new Color(204, 255, 204), new Color(153, 255, 153), 25);
        GoogleMaterialIcon iconCall = new GoogleMaterialIcon(GoogleMaterialDesignIcon.CALL, GradientType.VERTICAL, new Color(204, 255, 204), new Color(153, 255, 153), 25);
        Button call = new Button();
        Button videoCall = new Button();
        Button back = new Button();
        call.setIcon(iconCall.toIcon());
        videoCall.setIcon(iconVideoCall.toIcon());
        back.setIcon(iconBack.toIcon());
        panel.add(back, "height 40!");
        panel.add(avt, "height 40!");
        panel.add(labelTitle, "height 40!");
        panel.add(call, "height 40!");
        panel.add(videoCall, "height 40!");
        return panel;
    }

    private JPanel createBody() {
        RoundPanel panel = new RoundPanel();
        panel.setBackground(new Color(0, 0, 0, 0));
        panel.setLayout(new MigLayout("wrap,fillx"));
        return panel;
    }

    private JPanel createBottom() {
        RoundPanel panel = new RoundPanel();
        panel.setBackground(new Color(255, 255, 255, 20));
        panel.setLayout(new MigLayout("fill, inset 2", "[fill,40!]2[fill,40!]2[fill,40!]2[fill]2[fill,40!]", "[bottom]"));
        GoogleMaterialIcon iconFile = new GoogleMaterialIcon(GoogleMaterialDesignIcon.ATTACH_FILE, GradientType.VERTICAL, new Color(210, 210, 210), new Color(255, 255, 255), 25);
        GoogleMaterialIcon iconSend = new GoogleMaterialIcon(GoogleMaterialDesignIcon.SEND, GradientType.VERTICAL, new Color(255, 153, 153), new Color(255, 204, 51), 25);
        GoogleMaterialIcon iconEmot = new GoogleMaterialIcon(GoogleMaterialDesignIcon.INSERT_EMOTICON, GradientType.VERTICAL, new Color(210, 210, 210), new Color(255, 255, 255), 25);
        GoogleMaterialIcon iconCam = new GoogleMaterialIcon(GoogleMaterialDesignIcon.CAMERA_ENHANCE, GradientType.VERTICAL, new Color(210, 210, 210), new Color(255, 255, 255), 25);
        Button cmdFile = new Button();
        Button cmdSend = new Button();
        Button cmdEmot = new Button();
        Button cmdCam = new Button();
        cmdFile.setFocusable(false);
        cmdSend.setFocusable(false);
        cmdEmot.setFocusable(false);
        cmdCam.setFocusable(false);
        cmdFile.setIcon(iconFile.toIcon());
        cmdSend.setIcon(iconSend.toIcon());
        cmdEmot.setIcon(iconEmot.toIcon());
        cmdCam.setIcon(iconCam.toIcon());
        textMessage = new TextField();
        textMessage.setHint("Write a message here ...");
        textMessage.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                revalidate();
            }

            @Override
            public void keyTyped(KeyEvent ke) {
                runEventKeyTyped(ke);
            }
        });
        cmdSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runEventMousePressedSendButton(e);
            }
        });
        cmdFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runEventMousePressedFileButton(e);
            }
        });
        JScrollPane scroll = createScroll();
        scroll.setViewportView(textMessage);
        scroll.getViewport().setOpaque(false);
        scroll.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        scroll.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 0));
        panel.add(cmdFile, "height 40!");
        panel.add(cmdCam, "height 40!");
        panel.add(cmdEmot, "height 40!");
        panel.add(scroll);
        panel.add(cmdSend, "height 40!");
        return panel;
    }

    private JLayeredPane createLayeredPane() {
        JLayeredPane layer = new JLayeredPane();
        layoutLayered = new MigLayout("fill,inset 0", "[fill]", "[fill]");
        layer.setLayout(layoutLayered);
        return layer;
    }

    private Button createFloatingButton() {
        Button button = new Button();
        button.setBorder(null);
        FontAwesomeIcon icon = new FontAwesomeIcon(FontAwesome.ANGLE_DOWN, GradientType.VERTICAL, new Color(79, 79, 79, 240), new Color(248, 248, 248, 240), 35);
        button.setIcon(icon.toIcon());
        button.setRound(40);
        button.setBackground(new Color(100, 100, 100, 100));
        button.setPaintBackground(true);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                animationScroll.scrollVertical(scrollBody, scrollBody.getVerticalScrollBar().getMaximum());
            }
        });
        return button;
    }

    private JScrollPane createScroll() {
        JScrollPane scroll = new JScrollPane();
        scroll.setBorder(null);
        scroll.setViewportBorder(null);
        return scroll;
    }

    public void addChatBox(ModelMessage message, ChatBox.BoxType type) {
        int values = scrollBody.getVerticalScrollBar().getValue();
        if (type == ChatBox.BoxType.LEFT) {
            body.add(new ChatBox(type, message), "width ::80%");
        } else {
            body.add(new ChatBox(type, message), "al right,width ::80%");
        }
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                body.revalidate();
                scrollBody.getVerticalScrollBar().setValue(values);
                bottom.revalidate();
            }
        });
        body.repaint();
        body.revalidate();
        scrollBody.revalidate();
        scrollToBottom();
    }

    public void clearChatBox() {
        body.removeAll();
        body.repaint();
        body.revalidate();
    }

    private void scrollToBottom() {
        animationScroll.scrollVertical(scrollBody, scrollBody.getVerticalScrollBar().getMaximum());
    }

    private void runEventMousePressedSendButton(ActionEvent evt) {
        for (ChatEvent event : events) {
            event.mousePressedSendButton(evt);
        }
    }
    
    public void sentMessage(ActionEvent evt) {
        runEventMousePressedSendButton(evt);
    }

    private void runEventMousePressedFileButton(ActionEvent evt) {
        for (ChatEvent event : events) {
            event.mousePressedFileButton(evt);
        }
    }

    private void runEventKeyTyped(KeyEvent evt) {
        for (ChatEvent event : events) {
            event.keyTyped(evt);
        }
    }

    public String getText() {
        return textMessage.getText();
    }

    public void setTitle(String title) {
        labelTitle.setText(title);
    }

    public String getTitle() {
        return labelTitle.getText();
    }

    public void setText(String text) {
        textMessage.setText(text);
    }

    public void textGrabFocus() {
        textMessage.grabFocus();
    }

    public void clearTextAndGrabFocus() {
        textMessage.setText("");
        textMessage.grabFocus();
    }

    private MigLayout layout;
    private MigLayout layoutLayered;
    private JLayeredPane layeredPane;
    private JPanel header;
    private JPanel body;
    private JPanel bottom;
    private TextField textMessage;
    private JScrollPane scrollBody;
    private Button floatingButton;
    private JLabel labelTitle;
}
