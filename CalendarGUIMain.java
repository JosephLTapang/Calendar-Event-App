import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class CalendarGUIMain extends JFrame implements ActionListener {
  private JButton button1;
  private JLabel label1;
  private JLabel label2;
  private JLabel label3;
  private JLabel label4;
  private JLabel label5;
  private JLabel label6;
  private JLabel label7;
  private JLabel label8;
  private JRadioButton radioButton1;
  private JRadioButton radioButton2;
  private JRadioButton radioButton3;
  private JRadioButton radioButton4;
  private JRadioButton radioButton5;
  private JRadioButton radioButton6;
  private JRadioButton radioButton7;
  private JRadioButton radioButton8;

  private JTextField textfield1;
  private JTextField textfield2;
  private JTextField textfield3;
  private JTextField textfield4;
  private JTextField textfield5;
  // test things
  private JSpinner dateSpinner;
  // test things end
  
  //Moved classification and priority variables to main scope
  private int pri;
  private String classi;

  // this constructor adds buttons, labels, and text fields
  public CalendarGUIMain() {

    setLayout(new GridLayout(0, 1));

    label1 = new JLabel("Event Name");
    add(label1);
    textfield1 = new JTextField(15);
    add(textfield1);

    label2 = new JLabel("Event Location");
    add(label2);
    textfield2 = new JTextField(15);
    add(textfield2);

    label3 = new JLabel("YYYYMMDD");
    add(label3);
    textfield3 = new JTextField(15);
    add(textfield3);

    label4 = new JLabel("Event Time Start");
    add(label4);
    textfield4 = new JTextField(15);
    add(textfield4);

    label5 = new JLabel("Event Time End");
    add(label5);
    textfield5 = new JTextField(15);
    add(textfield5);

    /*
     * Still working on date spinner JSpinnerDate date = new Date();
     * SpinnerDateModel dSpinner = new SpinnerDateModel(date, null, null,
     * Calendar.DAY_OF_WEEK_IN_MONTH);dateSpinner = new
     * JSpinner(dSpinner);//TEST SPINNERadd(dateSpinner);Still working on date
     * spinner JSpinner
     */

    // Priority High, Medium, Low
    label6 = new JLabel("Priority");
    add(label6);
    pri = 5;
    radioButton1 = new JRadioButton("High");
    radioButton2 = new JRadioButton("Normal", true);
    radioButton3 = new JRadioButton("Low");
    add(radioButton1);
    add(radioButton2);
    add(radioButton3);
    groupButton1();

    // Is event recurring?
    label7 = new JLabel("Recurring Event");
    add(label7);
    radioButton4 = new JRadioButton("Yes");
    radioButton5 = new JRadioButton("No",true);
    add(radioButton4);
    add(radioButton5);
    groupButton2();

    // public,private,confidential
    label8 = new JLabel("Event Visibility");
    add(label8);
    classi = "Public";
    radioButton6 = new JRadioButton("Public", true);
    radioButton7 = new JRadioButton("Private");
    radioButton8 = new JRadioButton("Confidential");
    add(radioButton6);
    add(radioButton7);
    add(radioButton8);
    groupButton3();

    // Button to create .ics file once previous inputs are filled.
    button1 = new JButton("Create .ics");
    add(button1);
    button1.addActionListener(this);// Button is listening for event.
  }

  // Methods to group related radial buttons together.
  public void groupButton1() {
    ButtonGroup bg1 = new ButtonGroup();
    bg1.add(radioButton1);
    bg1.add(radioButton2);
    bg1.add(radioButton3);
    radioButton1.addItemListener(new HandlerClass(1));
    radioButton2.addItemListener(new HandlerClass(5));
    radioButton3.addItemListener(new HandlerClass(9));
  }

  public void groupButton2() {
    ButtonGroup bg2 = new ButtonGroup();
    bg2.add(radioButton4);
    bg2.add(radioButton5);
  }

  public void groupButton3() {
    ButtonGroup bg3 = new ButtonGroup();
    bg3.add(radioButton6);
    bg3.add(radioButton7);
    bg3.add(radioButton8);
    radioButton6.addItemListener(new HandlerClass2(radioButton6.getText()));
    radioButton7.addItemListener(new HandlerClass2(radioButton7.getText()));
    radioButton8.addItemListener(new HandlerClass2(radioButton8.getText()));
  }

  // Code relating to the creation of file after user is done and presses the
  // button.
  public void actionPerformed(ActionEvent e) {

    String locName = textfield2.getText();
    String summary = textfield1.getText();
    String date = textfield3.getText();
    String recur;//
    int clnum;//
    String endt = textfield5.getText();
    String startt = textfield4.getText();
    int priority;//
    TimeZone tz;
    StringBuilder sb;
    BufferedWriter writer = null;
    tz = Calendar.getInstance().getTimeZone();
    sb = new StringBuilder();

    System.out.println("Event to be added:\n");
    System.out.println("BEGIN:VCALENDAR");
    sb.append("BEGIN:VCALENDAR\n");
    System.out.println("PROID:-//Team Yangtze//yzcalendar 0.1//EN");
    sb.append("PROID:-//Team Yangtze//yzcalendar 0.1//EN\n");
    System.out.println("VERSION:1.0");
    sb.append("VERSION:1.0\n");
    System.out.println("BEGIN:VTIMEZONE");
    sb.append("BEGIN:VTIMEZONE\n");
    System.out.println("TZID:" + tz.getID());
    sb.append("TZID:" + tz.getID() + "\n");
    System.out.println("END:VTIMEZONE");
    sb.append("END:VTIMEZONE\n");
    System.out.println("BEGIN:VEVENT");
    sb.append("BEGIN:VEVENT\n");
    System.out.println("DTSTART:" + date + "T" + startt + "00Z");
    sb.append("DTSTART:" + date + "T" + startt + "00Z\n");
    System.out.println("DTEND:" + date + "T" + endt + "00Z");
    sb.append("DTEND:" + date + "T" + endt + "00Z\n");
    System.out.println("LOCATION:" + locName);
    sb.append("LOCATION:" + locName + "\n");
    System.out.println("SUMMARY:" + summary);
    sb.append("SUMMARY:" + summary + "\n");
    System.out.println("PRIORITY:"+pri);
    sb.append("PRIORITY:"+pri+"\n");
    System.out.println("CLASS:"+classi);
    sb.append("CLASS:"+classi+"\n");
    System.out.println("END:VEVENT");
    sb.append("END:VEVENT\n");
    System.out.println("END:VCALENDAR");
    sb.append("END:VCALENDAR\n");

    // IOException error may not apply with textfields and buttons.
    try {
      writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
          "yangtze.ics"), "utf-8"));
      writer.write(sb.toString());
    } catch (IOException exception) {
      System.out.println("Error writing to file");
    } finally {
      try {
        writer.close();
      } catch (Exception e2) {
        System.out.println("Error writing to file");
      }
    }
  }
  //Private handler class for priority radio buttons
  private class HandlerClass implements ItemListener
  {
    private int priority;
    public HandlerClass(int p){
      priority = p;
    }
    @Override
    public void itemStateChanged(ItemEvent event) {
      pri = priority;
      
    }
  }
  
  //Private handler class for classification radio buttons
  private class HandlerClass2 implements ItemListener
  {
    private String classification;
    public HandlerClass2(String s){
      classification = s;
    }
    @Override
    public void itemStateChanged(ItemEvent event) {
      classi = classification;
      
    }
  }
  public static void main(String args[]) {
    CalendarGUIMain gui = new CalendarGUIMain();
    gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    gui.setSize(400, 600);
    gui.setVisible(true);
    gui.setTitle("Calendar Event Generator");
  }
}
