package com.meme.core.util;

public class UserAgentUtil {

	public enum Browser {

		UCBrowser("UCBrowser",new String[]{"UCBrowser","UBrowser"}),//PC端UC浏览器的user-agent用UBrowser标识
		Firefox("Firefox",new String[]{"Firefox"}),
		IE("IE",new String[]{"Trident","MSIE"}),
		Opera("Opera",new String[]{"Opera","OPR"}),//PC端OPERA浏览器的user-agent用OPR标识
		QQBrowser("QQBrowser",new String[]{"QQBrowser","MQQBrowser"}),
		_360("360Browser",new String[]{"360SE","QIHU 360EE"}),
		Maxthon("Maxthon",new String[]{"Maxthon"}),
		Chrome("Chrome",new String[]{"Chrome"}),
		Safari("Safari",new String[]{"Safari"}),
		unknown("unknown",new String[]{"unknown"});
		
		/**浏览器名**/
		private String name;
		
		/**浏览器在user-agent字符串中的标识别名**/
		private String[] aliases;
		
		private Browser(String name, String[] aliases) {
			this.name = name;
			this.aliases = aliases;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String[] getAliases() {
			return aliases;
		}
		public void setAliaes(String[] aliases) {
			this.aliases = aliases;
		}
	}
	
	public enum Terminal {
		iPhone("iPhone"), iPad("iPad"), Android("Android"), Windows("Windows"),Macintosh("Macintosh"),Linux("Linux"),unknown("unknown");
		private String terminal;

		Terminal(String terminal) {
			this.terminal = terminal;
		}

		public String getTerminal() {
			return terminal;
		}

		public void setTerminal(String terminal) {
			this.terminal = terminal;
		}
	}
	/**
	 * 检测请求终端所用设备
	 * @param uaString
	 * @return
	 */
	public static String checkDevice(String uaString){
		String device=null;
		for(Terminal t:Terminal.values()){
			if(uaString.indexOf(t.getTerminal())>=0){
				//android设备的user-agent包含linux和android两个关键字，先检测Linux再检测android
				if(t.getTerminal().equals(Terminal.Linux.getTerminal())){
					if(uaString.indexOf(Terminal.Android.getTerminal())>=0) device=Terminal.Android.getTerminal();
					else device=t.getTerminal();
				}else if(t.getTerminal().equals(Terminal.Windows.getTerminal())){////UC浏览器android版操作系统标识为Windows
					String[] ucaliases=Browser.UCBrowser.getAliases();
					//UC浏览器PC端user-agent用UBrowser标识，android用UCBrowser
					if(uaString.indexOf(ucaliases[0])>=0) device=Terminal.Android.getTerminal();
					else device=t.getTerminal();
				}else{
					device=t.getTerminal();
				}
				break;
			}
		}
		if(StringUtil.isEmpty(device)) device=Terminal.unknown.getTerminal();
		return device;
	}
	
	/**
	 * 检测请求终端所用浏览器
	 * @param uaString
	 * @return
	 */
	public static String checkBrowser(String uaString){
		String browser=null;
		for(Browser b:Browser.values()){
			String[] aliases=b.getAliases();
			boolean flag=false;
			for(String s:aliases){
				if(uaString.indexOf(s)>=0){
					String[] operaAliases=Browser.Opera.getAliases();
					for(String o:operaAliases){
						if(uaString.indexOf(o)>=0) browser=Browser.Opera.getName();
					}
					if(StringUtil.isEmpty(browser)) browser=b.getName();
					flag=true;
					break;
				}
			}
			if(flag) break;
		}
		if(StringUtil.isEmpty(browser)) browser=Browser.unknown.getName();
		return browser;
	}
}
