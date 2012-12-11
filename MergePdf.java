/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import com.lowagie.text.pdf.PdfCopyFields;
import com.lowagie.text.pdf.PdfReader;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
/**
 *
 * @author compilr
 */
 public class MergePdf implements ActionListener
{
    String[] listfile1,listfile2;
    String filename,str1,str2;
    int check=0;
    private JFrame fr;
    private JButton bn1,bn2,bn3;
    private JTextField txt1,txt2;
    private JLabel l1,l2,workingLabel,doneLabel;
    private JFileChooser fc;
    String path1,path2;

 void createframe()
 {
    fr=new JFrame("pdf merging");
    fc=new JFileChooser();
    l1=new JLabel("Source");
    workingLabel=new JLabel("Merging...");
    doneLabel=new JLabel("Done");
    fr.add(l1);
    l1.setBounds(40, 40, 60, 20);
    txt1=new JTextField();
    fr.add(txt1);
    doneLabel.setBounds(100, 120, 60, 20);
    workingLabel.setBounds(100, 150, 100, 20);
doneLabel.setVisible(false);
workingLabel.setVisible(false);
fr.add(workingLabel);
fr.add(doneLabel);

 txt1.setBounds(120, 40, 140, 21);
    bn1=new JButton("Browse");
    fr.add(bn1);
    bn1.setBounds(270, 40, 90, 20);
    l2=new JLabel("Destination");
    fr.add(l2);
    l2.setBounds(40, 70, 80, 20);
    txt2=new JTextField();
    fr.add(txt2);
    txt2.setBounds(120, 70, 140, 21);
    bn2=new JButton("Browse");

    fr.add(bn2);
    bn2.setBounds(270, 70, 90, 20);
    bn3=new JButton("Merge");
    fr.add(bn3);
    bn3.setBounds(135, 100, 90, 20);
    fr.setSize(400,200);
    fr.setLayout(null);
    fr.setVisible(true);
    fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    bn1.addActionListener(this);
    bn2.addActionListener(this);
    bn3.addActionListener(this);
 }



    public void actionPerformed(ActionEvent e)
    {
if(e.getSource()==bn1)
{
    workingLabel.setVisible(false);
    doneLabel.setVisible(false);
    fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                int returnval=fc.showOpenDialog(fr);
                path1=fc.getSelectedFile().toString();
                path1=path1.replace('\\' , '/');
               
                txt1.setText(path1);
}
if(e.getSource()==bn2)
{
    workingLabel.setVisible(false);
    doneLabel.setVisible(false);
    fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                int returnval=fc.showOpenDialog(fr);
                path2=fc.getSelectedFile().toString();
                path2=path2.replace('\\' , '/');
               
                txt2.setText(path2);
}
if(e.getSource()==bn3)
{
    workingLabel.setVisible(true);
    search(path1,path2);
    doneLabel.setVisible(true);
    workingLabel.setVisible(false);

}


    }






    public void search(String s1,String s2)
    {
        File f1=new File(s1);
        File f2=new File(s2);
        listfile1=f1.list();//source1
        listfile2=f2.list();//dest
      
        System.out.print("list "+listfile1.length+"   "+listfile2.length);

              

                
        for(int i=0;i<listfile2.length;i++)
        {
           
           
             filename=listfile2[i];
               check=0;
            for(int j=0;j<listfile1.length;j++)
            {
            if(listfile1[j].equals(listfile2[i]))
            {              
                check=1;
                str1=s1+"/"+listfile1[j];
                str2=s2+"/"+filename;
               
                try
                {
                     merge(str1,str2,s1,filename);
                }
                catch(Exception e)
                {

                }
            }
          }
        
        if(check==0)
        {
            
           
             File ft2=new File(s1+"/"+filename);
             File f3=new File(s2+"/"+filename);
            // Create an instance of source and destination files
            FileInputStream fis = null;
            FileOutputStream fos = null;
            try
            {
                fis = new FileInputStream(f3);
                fos = new FileOutputStream(ft2);
                // Define the size of our buffer for buffering file data
                byte[] buffer = new byte[4096];
                int read;
                while ((read = fis.read(buffer)) != -1)
                {
                    fos.write(buffer, 0, read);
                }

            }
            catch (IOException e)
            {
                    e.printStackTrace();
            }
            finally
            {
                try
                {

                        // Finally close the input and output stream after we've finished with them.
                    if (fis != null)
                    {
                        fis.close();
                    }
                    if (fos != null)
                    {
                        fos.close();
                    }
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        }

        File delFile;
        for(int k=0;k<listfile1.length;k++)
                {
          
                    delFile=new File(s2+"/"+listfile1[k].toString());
                    delFile.delete();

                }
    }
    void merge(String st1,String st2,String path,String filename1)throws Exception
    {
       
        PdfReader reader1 = new PdfReader(st1);
        PdfReader reader2 = new PdfReader(st2);
        File temp1=new File(path+"/temp1");
        temp1.mkdir();
        PdfCopyFields copy = new PdfCopyFields(new FileOutputStream(path+"/temp1/"+filename1));
        copy.addDocument(reader1);
        copy.addDocument(reader2);
        copy.close();
        File f1=new File(st1);
        f1.delete();
       // File f2=new File(st2);
        File f3=new File(path+"/temp1/"+filename1);
            // Create an instance of source and destination files
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try
        {
           
            fis = new FileInputStream(f3);
            fos = new FileOutputStream(f1);
            // Define the size of our buffer for buffering file data
            byte[] buffer = new byte[4096];
            int read; 
            while ((read = fis.read(buffer)) != -1)
            {
                fos.write(buffer, 0, read);
            }
        }
        catch (IOException e)
        {
                e.printStackTrace();
        }
        finally
        {
            try
            {

                    // Finally close the input and output stream after we've finished with them.
                if (fis != null)
                {
                    fis.close();
                }
                if (fos != null)
                {
                    fos.close();
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        if( temp1.exists() )
        {
            
            File[] files = temp1.listFiles();
            for(int i=0; i<files.length; i++)
          {
               files[i].delete();
          }
            temp1.delete();
        }
    }


    public static void main(String[] args)
    {
    MergePdf mpdf=new MergePdf();
    mpdf.createframe();
    }

    }


