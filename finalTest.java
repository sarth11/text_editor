import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.regex.*;
class Editor extends WindowAdapter implements ActionListener,KeyListener,MouseListener
{	
	String infomessage,path=null,name=null;
	int count=0,i=0,currentCaretPosition=0,no=1;
	TextField txt,rpwith;
	int pos=0;
	private Frame f,finner,fmsg;
	private MenuBar mb;
	private Menu file,tool;
	private Panel p;
	private TextArea contents;
	private MenuItem nw,opn,sve,sveas,exit,find,find_replace;
	FileDialog fd;
	Button b1,b2,b3,fndnxt,cancel,rp,rpall,bo1,bo2,bo3,be1,be2,be3;
	Boolean opentemp=false,first=false,saved=false,opencheck=true,newcheck=true,savecheck=false,success=false;
	public static void main(String args[])
	{
		Editor t=new Editor();	
	}
	public Editor()
	{
		fileMenu();
		toolMenu();
		createTextArea();
		createEditor();	
	}
	private void fileMenu()
	{
		file=new Menu("File");
		nw=new MenuItem("New");
		opn=new MenuItem("Open");
		sve=new MenuItem("Save");
		sveas=new MenuItem("SaveAs");
		exit=new MenuItem("Exit");
		nw.addActionListener(this);
		opn.addActionListener(this);
		sve.addActionListener(this); 
		sveas.addActionListener(this);
		exit.addActionListener(this);
		file.add(nw);
		file.add(opn);
		file.add(sve);
		file.add(sveas);
		file.add(exit);
		
	}
	private void toolMenu()
	{
		tool=new Menu("Tools");
		find=new MenuItem("Find");
		find_replace=new MenuItem("Replace");
		find.addActionListener(this);
		find_replace.addActionListener(this);
		tool.add(find);
		tool.add(find_replace);
	}
	private TextArea createTextArea()
	{
		contents=new TextArea();
		return contents;
	}
	private void createEditor()
	{
		f=new Frame();
		f.setVisible(true);
		createMenuBar();
		f.setMenuBar(mb);
		f.add(contents);
		contents.addKeyListener(this);
		f.addWindowListener(this);
		f.setSize(600,600);
		
	}
	private MenuBar createMenuBar()
	{
		mb=new MenuBar();
		mb.add(file);
		mb.add(tool);
		return mb;
	}
	public void mouseClicked(MouseEvent me)
	{
		System.out.println("1");
		pos = contents.getCaretPosition();
	}
	public void mouseEntered(MouseEvent me)
	{
	}
	public void mouseExited(MouseEvent me)
	{
	}
	public void mousePressed(MouseEvent me)
	{
	}
	public void mouseReleased(MouseEvent me)
	{
	}
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==opn)
		{
			if(!opencheck)
			{
				saveforopen();
			}
			else
			{
				opens();
			}
			
		}
		else if(e.getSource()==exit)
		{
			if(!newcheck==true&&!opencheck==true)
			{
				closesave();		
			}
			else
			{
				System.exit(0);
			}
		}
		else if (e.getSource()==nw)
		{
			if(newcheck)
			{
				name=null;
				path=null;
				contents.setText("");
			}
			else
			{
				newfile();
			}
		}
		else if(e.getSource()==b3)
		{
			finner.setVisible(false);
		}
		else if(e.getSource()==bo3)
		{
			finner.setVisible(false);
		}
		else if(e.getSource()==sve)
		{
			if(path==null&&name==null)
				savingas();
			else
				saving(path,name);
		}
		else if(e.getSource()==b1)
		{
			finner.setVisible(false);
			savingas();
			if(success==true)
			{
				newcheck=true;
				opencheck=true;
				contents.setText("");
			}		
		}
		else if(e.getSource()==be1)
		{
			finner.setVisible(false);
			savingas();
			System.exit(0);
		}
		else if(e.getSource()==bo1)
		{
			finner.setVisible(false);
			savingas();
			if(success==true)
			{
				newcheck=true;
				opencheck=true;
				contents.setText("");
				opens();
			}	
		}
		else if(e.getSource()==b2)
		{
			resetnew();
			opencheck=true;
			newcheck=true;
		}
		else if (e.getSource()==bo2)
		{
			reset();
			opencheck=true;
			newcheck=true;
		}
		else if(e.getSource()==sveas)
		{
			savingas();
		}
		else if(e.getSource()==find)
		{
			findword();
		}
		else if(e.getSource()==fndnxt)
		{
			contents.setCaretPosition(pos);
			System.out.println("pos="+pos);
			String str=txt.getText();
			contents.addMouseListener(this);
			//contents.setCaretPosition(currentCaretPosition);
			System.out.println("caret="+currentCaretPosition);
			pos=contents.getText().indexOf(str,pos);
			int strleng=str.length();
			String s=contents.getText();
			int i=0;
			count=0;
			while(i<pos)
			{
				if(s.charAt(i)==13)
				count++;
				i++;
			}
			if(pos==-1)
			{
				WarningFind(str);
				pos=0;
			}
			else
			{
				f.toFront();
				contents.select(pos-count,pos+strleng-count);
				
				pos++;
			}	
			
		}
		else if(e.getSource()==cancel)
		{
			finner.setVisible(false);
			no=1;
		}
		else if(e.getSource()==find_replace)
		{
			replace();
		}
		else if(e.getSource()==rp)
		{
			change();
		}
		else if (e.getSource()==rpall)
		{
			changeall();
		}
		else if(e.getSource()==be2)
		{
			System.exit(0);
		}
		else if(e.getSource()==be3)
		{
			finner.setVisible(false);
		}
	}
	public void windowClosing(WindowEvent e)
	{
		if(!newcheck==true&&!opencheck==true)
		{
			saveexit();		
		}
		else 
		System.exit(0);
	}
	private void newfile()
	{
		finner=new Frame();
		Label l=new Label("Do you want to save file ");
		b1=new Button("Save");
		b2=new Button("Don't save");
		b3=new Button("Cancel");
		finner.add(l);
		Panel p=new Panel(new GridLayout(0,5));
		p.add(new Label(""));
		p.add(new Label(""));
		p.add(b1);
		p.add(b2);
		p.add(b3);
		finner.add(p,"South");
		finner.setSize(400,200);
		finner.setVisible(true);
		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
	}
	public void keyTyped(KeyEvent k)
	{
		saved=false;
		newcheck=false;
		opencheck=false;
	}
	public void keyReleased(KeyEvent k)
	{
	}
	public void keyPressed(KeyEvent k)
	{
	}
	public void save()
	{
		finner=new Frame();
		Label l=new Label(" Do you want to save file  ");
		b1=new Button("Save");
		b2=new Button("Don't Save");
		b3=new Button("Cancel");
		finner.add(l);
		Panel p=new Panel(new GridLayout(0,5));
		p.add(new Label(""));
		p.add(new Label(""));
		p.add(b1);
		p.add(b2);
		p.add(b3);
		finner.add(p,"South");
		finner.setSize(400,200);
		finner.setVisible(true);
		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
	}
	public void opens()
	{
		fd=new FileDialog(f,"OPEN",FileDialog.LOAD);
		fd.setVisible(true);
		name=fd.getFile();
		path=fd.getDirectory();
		System.out.println("You have selected "+name+"in "+path);
		try
		{	
			FileReader fis=new FileReader(path+"//"+name);
			BufferedReader bis=new BufferedReader(fis);
			String ch;
			contents.setText("");
			while((ch=bis.readLine())!=null)
				contents.append(ch+"\n");
			bis.close();
			contents.addKeyListener(this);
		}
		catch(IOException i)
		{
			System.out.println(i.getMessage());
		}
	}
	public void saving(String path,String name)
	{
		try
		{
			File filename=new File(path+"\\"+name);
			BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
            		String str1=contents.getText();
			writer.write(str1);
			writer.close();
			opencheck=true;
			newcheck=true;
			success=true;
        	}
		catch (IOException err)
		{
            		System.out.println(err.getMessage());
        	}
	}
	public void savingas()
	{
		System.out.println("ENTERED");
		fd=new FileDialog(f,"SaveAs",FileDialog.SAVE);
		fd.setVisible(true);
		name=fd.getFile();
		path=fd.getDirectory();
		if(name==null)
		{
			fd.setVisible(false);
			System.out.println("DON!");
			success=false;
		}
		else
		{
			saving(path,name);
			System.out.println("DON@");
		}
	}
	public void findword()
	{
		finner=new Frame();
		Label l=new Label(" Find What ");
		txt=new TextField();
		Panel pend=new Panel(new GridLayout(0,5));
		Panel p=new Panel(new GridLayout(5,0));
		p.add(txt);
		fndnxt=new Button(" Find Next ");
		cancel=new Button("Cancel");
		Label free1=new Label();
		Label free2=new Label();
		Label free3=new Label();
		pend.add(fndnxt);
		pend.add(free1);
		pend.add(free2);
		pend.add(free3);
		pend.add(cancel);
		cancel.addActionListener(this);
		finner.add(pend,"South");
		finner.add(l,"North");
		finner.add(p);
		finner.setSize(350,200);
		finner.setVisible(true);
		fndnxt.setEnabled(false);
		txt.addKeyListener(new KeyListener()
		{
			public void keyTyped(KeyEvent k)
			{
			
				
			}
			public void keyPressed(KeyEvent k)
			{
			}
			public void keyReleased(KeyEvent k)
			{
				if(txt.getText().equals(""))
				{
					fndnxt.setEnabled(false);
				}
				else
				{
					fndnxt.setEnabled(true);
				}
			}
		});
		fndnxt.addActionListener(this);
		finner.addWindowListener(new WindowListener()
			{
			public void windowClosing(WindowEvent w)
			{	
				finner.setVisible(false);
				no=1;
			}
			public void windowDeactivated(WindowEvent w){}
			public void windowActivated(WindowEvent w){}
			public void windowClosed(WindowEvent w){}
			public void windowIconified(WindowEvent w){}
			public void windowDeiconified(WindowEvent w){}
			public void windowOpened(WindowEvent w){}
		});
	}
	public void WarningFind(String s)
	{
		infomessage="Cannot find"+"\""+s+"\"";
		Label message;
		Button ok;
		fmsg=new Frame();
		fmsg.setSize(180,100);
		fmsg.setTitle("TEXT EDITOR");
		fmsg.setLayout(new GridBagLayout());
		GridBagConstraints gbc=new GridBagConstraints();
		message=new Label(infomessage);
		gbc.gridx=0;
		gbc.gridy=0;
		gbc.ipadx=2;
		gbc.ipady=2;
		fmsg.add(message,gbc);
		ok=new Button("OK");
		ok.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent o)
			{
				fmsg.setVisible(false);	
				fmsg.dispose();
				finner.setVisible(false);
			}
		});
		fmsg.addWindowListener(new WindowListener()
		{
			public void windowClosing(WindowEvent w)
			{	
				fmsg.setVisible(false);
			}
			public void windowDeactivated(WindowEvent w){}
			public void windowActivated(WindowEvent w){}
			public void windowClosed(WindowEvent w){}
			public void windowIconified(WindowEvent w){}
			public void windowDeiconified(WindowEvent w){}
			public void windowOpened(WindowEvent w){}
		});
		gbc.gridx=0;
		gbc.gridy=1;
		gbc.ipadx=1;
		gbc.ipady=2;
		fmsg.add(ok,gbc);
		fmsg.setVisible(true);
	}
	public void changeall()
	{
		String s1=txt.getText();
		String s2=rpwith.getText();
		String stri=null;
		String strarea=contents.getText();
		Pattern p=Pattern.compile(s1);
		Matcher m=p.matcher(strarea);
		if(m.find())
		{
			stri=m.replaceAll(s2);
			contents.setText(stri);
		}
	}
	public void change()
	{
			int st,en;
			String str=txt.getText();
			st=contents.getSelectionStart();
			en=contents.getSelectionEnd();
			System.out.println("Position:"+pos);
			int strleng=str.length();
			System.out.println("START: "+st+"END: "+en);
			if(st==en)
			{	
				pos=contents.getText().indexOf(str,pos);
				if(pos==-1)
				{
					WarningFind(str);
					pos=0;
				}
				else
				{
					i=0;count=0;
					String s=contents.getText();
					while(i<pos)
					{
						if(s.charAt(i)==13)
						count++;
						i++;	
					}
					contents.select(pos-count,pos+strleng-count);
					f.toFront();
					System.out.println("Selected text"+contents.getText().substring(pos,pos+strleng));
				}
			}
			else
			{
				st=contents.getSelectionStart();
				en=contents.getSelectionEnd();
				System.out.println("start:"+st+"end"+en);
				System.out.println("count"+count+"length"+strleng);
				contents.replaceText(rpwith.getText(),st-count,strleng+st-count);
				f.toFront();
				pos++;
				pos=contents.getText().indexOf(str,pos);
				if(pos==-1)
				{
					WarningFind(str);
					pos=0;
				}
				else
				{
					i=0;count=0;
					String s=contents.getText();
					while(i<pos)
					{
						if(s.charAt(i)==13)
						count++;
						i++;	
					}
					contents.select(pos-count,pos+strleng-count);
					f.toFront();
				}
				newcheck=false;
				opencheck=false;
			}
	}
	public void reset()
	{
		contents.setText("");
		finner.setVisible(false);
		opens();
	}
	public void resetnew()
	{
		contents.setText("");
		finner.setVisible(false);
	}
	public void saveforopen()
	{
		finner=new Frame();
		Label l=new Label("Do you want to save file ");
		bo1=new Button("Save");
		bo2=new Button("Don't Save");
		bo3=new Button("Cancel");
		finner.add(l);
		Panel p=new Panel(new GridLayout(0,5));
		p.add(new Label(""));
		p.add(new Label(""));
		p.add(bo1);
		p.add(bo2);
		p.add(bo3);
		finner.add(p,"South");
		finner.setSize(400,200);
		finner.setVisible(true);
		bo1.addActionListener(this);
		bo2.addActionListener(this);
		bo3.addActionListener(this);
	}
	public void closesave()
	{
		finner=new Frame();
		Label l=new Label("Do you want to save file ");
		b1=new Button("Save");
		b2=new Button("Don't Save");
		b3=new Button("Cancel");
		finner.add(l);
		Panel p=new Panel(new GridLayout(0,5));
		p.add(new Label(""));
		p.add(new Label(""));
		p.add(b1);
		p.add(b2);
		p.add(b3);
		finner.add(p,"South");
		finner.setSize(400,200);
		finner.setVisible(true);
		b1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ex)
			{
				finner.setVisible(false);
				savingas();
				if(success==true)
				System.exit(0);
			}
		});
		b2.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ex)
			{
				System.exit(0);
			}
		});
		b3.addActionListener(this);
		
	}
	public void replace()
	{
		finner=new Frame();
		Label l=new Label("Find What");
		Label rpstr=new Label("Replace With");
		txt=new TextField();
		rpwith=new TextField();
		Panel pend=new Panel(new GridLayout(0,5));
		Panel p=new Panel(new GridLayout(5,0));
		p.add(txt);
		p.add(rpstr);
		p.add(rpwith);
		fndnxt=new Button("Find Next");
		rp=new Button("Replace");
		rpall=new Button("Replace All");
		cancel=new Button("Cancel");
		Label free1=new Label();
		pend.add(fndnxt);
		pend.add(free1);
		pend.add(rp);
		pend.add(rpall);
		pend.add(cancel);
		cancel.addActionListener(this);
		rp.addActionListener(this);
		rpall.addActionListener(this);
		finner.add(pend,"South");
		finner.add(l,"North");
		finner.add(p);
		finner.setSize(350,200);
		finner.setVisible(true);
		fndnxt.setEnabled(false);
		rp.setEnabled(false);
		rpall.setEnabled(false);
		txt.addKeyListener(new KeyListener()
		{
			public void keyTyped(KeyEvent k)
			{
			}
			public void keyPressed(KeyEvent k)
			{
			}
			public void keyReleased(KeyEvent k)
			{
				if(txt.getText().equals(""))
				{
					rp.setEnabled(false);
					rpall.setEnabled(false);
					fndnxt.setEnabled(false);
				}
				else
				{
					pos=0;
					fndnxt.setEnabled(true);
					rp.setEnabled(true);
					rpall.setEnabled(true);
				}
			}
		});
		fndnxt.addActionListener(this);
		finner.addWindowListener(new WindowListener()
			{
			public void windowClosing(WindowEvent w)
			{	
				finner.setVisible(false);
			}
			public void windowDeactivated(WindowEvent w){}
			public void windowActivated(WindowEvent w){}
			public void windowClosed(WindowEvent w){}
			public void windowIconified(WindowEvent w){}
			public void windowDeiconified(WindowEvent w){}
			public void windowOpened(WindowEvent w){}
		});
	}
	public void saveexit()
	{
		finner=new Frame();
		Label l=new Label(" Do you want to save file  ");
		be1=new Button("Save");
		be2=new Button("Don't Save");
		be3=new Button("Cancel");
		finner.add(l);
		Panel p=new Panel(new GridLayout(0,5));
		p.add(new Label(""));
		p.add(new Label(""));
		p.add(be1);
		p.add(be2);
		p.add(be3);
		finner.add(p,"South");
		finner.setSize(400,200);
		finner.setVisible(true);
		be1.addActionListener(this);
		be2.addActionListener(this);
		be3.addActionListener(this);
	}
}