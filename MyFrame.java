/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wordLadder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
/**
 *
 * @author wliu4
 */
public class MyFrame extends JFrame implements ActionListener
{
    // Global Components
    int frameNum = 1;
    JPanel backGround;
    int wordLength;
    // Frame 1 components
    JButton submitButton1;
    JTextField startWord;
    JTextField endWord;
    JLabel errorMessage1;
    // Frame 2 components and variables
    JLabel wordListPanel;
    JButton submitButton2;
    JButton resetButton1;
    JTextField addWord;
    JLabel errorMessage2;
    JPanel form2;
    ArrayList<String> wordList = new ArrayList<String>();
    String finalWord;
    
    public MyFrame()
    {
        setTitle("WordLadder");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450,400);
        setResizable(false);
        
    }
    public void changeFrame()
    {
        getContentPane().removeAll();
        repaint();
        backGround = new JPanel();
        backGround.setBackground(new Color(119, 136, 153));
        backGround.setLayout(new BoxLayout(backGround,BoxLayout.Y_AXIS));
       if(frameNum == 1)
       {
        JPanel form1 = new JPanel();
        
        submitButton1 = new JButton("Submit");
        submitButton1.addActionListener(this);

        startWord = new JTextField();
        startWord.setMaximumSize(new Dimension(500,30));
        startWord.addActionListener(this);

        endWord = new JTextField();
        endWord.setMaximumSize(new Dimension(500,30));
        endWord.addActionListener(this);
        
        errorMessage1 = new JLabel("");
        errorMessage1.setForeground(Color.red);
        
        backGround.add(new JLabel("<html><h1>WordLadder</h1></html>"));
        form1.setMaximumSize(new Dimension(350,300));
        form1.add(new JLabel("<html><p>Insert your starting and ending words.<br>"
                + "They must be of the same length and more<br> than 1 letter"
                + " apart.</p></html>"));
        form1.add(new JLabel("Start Word: "));
        form1.add(startWord);
        form1.add(new JLabel("End Word: "));
        form1.add(endWord);
        form1.add(errorMessage1);
        form1.add(submitButton1);
        form1.setLayout(new BoxLayout(form1,BoxLayout.Y_AXIS));
        
        backGround.add(form1);
        add(backGround);
        //pack();
        setVisible(true);
        }
        if(frameNum == 2)
        {
            form2 = new JPanel();
            wordList.add(startWord.getText());
            finalWord =  endWord.getText();
            wordListPanel = new JLabel("<html><p>"+ wordsToHTML(wordList)
                       +"&#8674<wbr>"+ finalWord + "</p><html>");
            wordListPanel.setFont(new Font("TimesRoman", Font.BOLD, 20));
            
            backGround.add(wordListPanel);

            
            
            form2.setMaximumSize(new Dimension(400,120));
            form2.setLayout(new BoxLayout(form2,BoxLayout.Y_AXIS));
            
            
            addWord = new JTextField();
            addWord.setMaximumSize(new Dimension(300,30));
            addWord.addActionListener(this);
            
            submitButton2 = new JButton("Add Word");
            submitButton2.addActionListener(this);
            
            resetButton1 = new JButton("Remove Previous Word");
            resetButton1.addActionListener(this);
            
            errorMessage2 = new JLabel("");
            errorMessage2.setForeground(Color.red);
            

            

            form2.add(addWord);
            form2.add(submitButton2);
            form2.add(resetButton1);
            form2.add(errorMessage2);

          
            backGround.add(form2);  
            add(backGround);
            setVisible(true);
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e)  {
        //Frame 1
        if(e.getSource() == submitButton1 || e.getSource() == startWord || 
                e.getSource() == endWord)
        {
            String word1 = startWord.getText().toLowerCase().trim();
            String word2 = endWord.getText().toLowerCase().trim();
            if(word1.length() != word2.length())
            {
                errorMessage1.setText("The given strings are not the same length!");
                return;
            }
            if(isAdjacent(word1,word2) || word1.equals(word2))
            {
                errorMessage1.setText("The given strings are not more than one character apart!");
                return;
            }
            try 
            {
                String errorMsg = "";
                if(!isWord(word1))
                {
                    errorMsg += "The given string in \"Start Word\" is not a real word!<br>";
                }
                if(!isWord(word2))
                {
                    errorMsg += "The given string in \"End Word\" is not a real word!<br>";
                }
                
                if(errorMsg.length() == 0)
                {
                    ++frameNum;
                    wordLength = word1.length();
                    startWord.setText(word1);
                    endWord.setText(word2);
                    changeFrame();
                }
                else
                {
                    errorMsg = "<html>" + errorMsg + "</html>";
                    errorMessage1.setText(errorMsg);
                }
            } 
            catch (FileNotFoundException ex) 
            {
                Logger.getLogger(MyFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        // Frame 2
        if(e.getSource() == submitButton2 || e.getSource() == addWord)
        {
            String prevWord = wordList.get(wordList.size()-1);
            String currWord = addWord.getText().toLowerCase().trim();
            addWord.setText("");
            errorMessage2.setText("");
            if(currWord.length() != wordLength)
            {
                errorMessage2.setText("<html>The inputted string is not " + wordLength +
                        " characters long!</html>");
                return;
            }
            if(!isAdjacent(currWord,prevWord))
            {
                errorMessage2.setText("<html>The inputted string is not one<wbr> character different"
                        + " from the previous word!</html>" );
                return;
            }
            try {
                if(!isWord(currWord))
                {
                    errorMessage2.setText("The inputted string is not a word!");
                    return;
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(MyFrame.class.getName()).log(Level.SEVERE, null, ex);
            }

            wordList.add(currWord);
            if(isAdjacent(finalWord,currWord))
            {
                String adjHTML = wordsToHTML(wordList);
                adjHTML = adjHTML.substring(0,adjHTML.length()-10);
                wordListPanel.setText("<html><p>"+ adjHTML
                        + finalWord + "</p><html>");
                form2.removeAll();
                form2.repaint();
                form2.add(new JLabel("<html><h2>Congratulations, you have completed "
                        + "the word ladder!</h2></html>"));
                return;
            }
            wordListPanel.setText("<html><p>"+ wordsToHTML(wordList)
                        +"&#8674<wbr>"+ finalWord + "</p><html>");
            
        }
        if(e.getSource() == resetButton1)
        {
            if(wordList.size() > 1)
            {
                wordList.remove(wordList.size()-1);
                wordListPanel.setText("<html><p>"+ wordsToHTML(wordList)
                       +"&#8674<wbr>"+ finalWord + "</p><html>");           
            }
        }
    } 
    
    //Finds if a given string is an actual word
    private boolean isWord(String word) throws FileNotFoundException
    {
        File myObj = new File("WordsByLength.TXT");
        Scanner myReader = new Scanner(myObj);
        while(myReader.hasNextLine())
        {
            if(myReader.nextLine().equals(word))
            {
                return true;
            }
        }
        return false;
    }
    // Finds if the inputted strings are exactly 1
    // character different (Order matters)
    private boolean isAdjacent(String word1, String word2)
    {
        int mismatches = 0;
        for(int i = 0; i < word1.length(); ++i)
        {
            if(word1.charAt(i) != word2.charAt(i))
            {
                mismatches += 1;
            }
        }
        return (mismatches == 1);
    }
    
    // Takes an arraylist of strings and
    // create a string that takes all of them in order
    // and connected by arrows
    private String wordsToHTML(ArrayList<String> words)
    {
        String result = "";
        for(String a: words)
        {
            result += a + "&#8594<wbr>";
        }
        result += "[Add Word]";
        return result;
    }
}
