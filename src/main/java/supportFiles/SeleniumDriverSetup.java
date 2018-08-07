package supportFiles;

import java.io.File;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.browserlaunchers.CapabilityType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.selendroid.client.SelendroidDriver;
import io.selendroid.common.SelendroidCapabilities;


public class SeleniumDriverSetup extends Thread {   


	
		
	@SuppressWarnings("deprecation")
	public  static WebDriver getDriver(String strBrowser) throws Exception
	{
		WebDriver driver = null;
		
		if(strBrowser.trim().toUpperCase().equals("IE"))
		{
			if (GlobalConstants.executionMode.equals("Grid"))
			{
				
				DesiredCapabilities cap= DesiredCapabilities.internetExplorer();
				
				if(GlobalConstants.nodeOS.equalsIgnoreCase("WINDOWS"))				
					cap.setPlatform(Platform.WINDOWS);
				else
					cap.setPlatform(Platform.LINUX);
				
				driver= new RemoteWebDriver(new URL("http://"+GlobalConstants.gridIPAddressPort+"/wd/hub"), cap);
				
				
			}else{
				//System.out.println("IE driver created");
				System.setProperty("webdriver.ie.driver", GlobalConstants.librariesPath +"IEDriverServer.exe");

				//DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
				//ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
				//driver= new InternetExplorerDriver(ieCapabilities);
				
				DesiredCapabilities capabilities = new DesiredCapabilities(); 
				//capabilities.internetExplorer();
				capabilities.setPlatform(Platform.WINDOWS);
				capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
				capabilities.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING,true);
				//capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true); 
				driver = new InternetExplorerDriver(capabilities); 
				driver.manage().window().maximize();
				
			}
			
			
		}else if(strBrowser.trim().toUpperCase().equals("FIREFOX"))
		{
			if(GlobalConstants.executionMode.equals("Grid")){
				
				DesiredCapabilities cap= DesiredCapabilities.firefox();
				if(GlobalConstants.nodeOS.equalsIgnoreCase("WINDOWS"))				
					cap.setPlatform(Platform.WINDOWS);
				else
					cap.setPlatform(Platform.LINUX);
				
				driver= new RemoteWebDriver(new URL("http://"+GlobalConstants.gridIPAddressPort+"/wd/hub"), cap);
				
			}else{
				
				if(GlobalConstants.platform.equalsIgnoreCase("Windows")){
					System.setProperty("webdriver.gecko.driver",GlobalConstants.librariesPath +"geckodriver.exe"); 			
				
				}else{
					System.setProperty("webdriver.gecko.driver",GlobalConstants.librariesPath +"geckodriver");
				
				}
				FirefoxProfile firefoxprofile = new FirefoxProfile();
				/*ProfilesIni profile= new ProfilesIni();
				firefoxprofile=profile.getProfile("default");*/
				DesiredCapabilities capability = DesiredCapabilities.firefox();
				
				if(GlobalConstants.platform.equalsIgnoreCase("Windows")){
				
					firefoxprofile.setAssumeUntrustedCertificateIssuer(true);
					
					//capability.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true); 
					capability.setCapability(CapabilityType.SUPPORTS_APPLICATION_CACHE, true); 
					//capability.setCapability(CapabilityType.SUPPORTS_BROWSER_CONNECTION, true); 
					capability.setCapability(CapabilityType.SUPPORTS_LOCATION_CONTEXT, true); 
					//capability.setCapability("nativeEvents", true);
					capability.setCapability(FirefoxDriver.PROFILE, firefoxprofile);
					//capability.setCapability(CapabilityType.HAS_NATIVE_EVENTS, true);
					capability.setPlatform(Platform.WINDOWS);
				
					//capability.setCapability(CapabilityType.PROXY, true);
					//System.out.println("has set cap");
					
				}/*else
				{
					capability.setPlatform(Platform.WINDOWS);
					firefoxprofile.setAssumeUntrustedCertificateIssuer(true);					
					//capability.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true); 
					capability.setCapability(CapabilityType.SUPPORTS_APPLICATION_CACHE, true); 
					//capability.setCapability(CapabilityType.SUPPORTS_BROWSER_CONNECTION, true); 
					capability.setCapability(CapabilityType.SUPPORTS_LOCATION_CONTEXT, true); 
					//capability.setCapability("nativeEvents", true);
					capability.setCapability(FirefoxDriver.PROFILE, firefoxprofile);
					//capability.setCapability(CapabilityType.HAS_NATIVE_EVENTS, true);
					
				}*/
				driver = new FirefoxDriver(capability);
				driver.manage().window().maximize();
				
				//System.out.println("Firefox Started");
				     
				
			}		
						
		}
		
		else if(strBrowser.trim().toUpperCase().equals("CHROME"))
		{
			if(GlobalConstants.executionMode.equalsIgnoreCase("Grid")){
				DesiredCapabilities cap= DesiredCapabilities.chrome();
				if(GlobalConstants.nodeOS.equalsIgnoreCase("WINDOWS"))				
					cap.setPlatform(Platform.WINDOWS);
				else
					cap.setPlatform(Platform.LINUX);
				
				driver= new RemoteWebDriver(new URL("http://"+GlobalConstants.gridIPAddressPort+"/wd/hub"), cap);
				
			}else{
				DesiredCapabilities capabilities=DesiredCapabilities.chrome();
			
				if(GlobalConstants.platform.equalsIgnoreCase("Windows")){
					System.setProperty("webdriver.chrome.driver",GlobalConstants.librariesPath +"chromedriver.exe"); 			
				
				}else{
					System.setProperty("webdriver.chrome.driver",GlobalConstants.librariesPath +"chromedriver");
					
					
				}
				driver = new ChromeDriver(capabilities);
					
			
			driver.manage().window().maximize();
			//System.out.println("***CHROME DRIVER HAS BEEN CREATED");
			
			}
			
		}else if(strBrowser.trim().toUpperCase().equals("HEADLESS")){
			
			/*// File path=new File("D:\\MyAllDocuments\\Technical\\Selenium Files\\phantomjs-2.1.1-windows\\phantomjs-2.1.1-windows\\bin\\phantomjs.exe");
			 //System.setProperty("phantomjs.binary.path",path.getAbsolutePath());
			
			//System.setProperty("phantomjs.binary.path", GlobalConstants.librariesPath +"phantomjs.exe");
			DesiredCapabilities capabilities=DesiredCapabilities.htmlUnit();
			capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			capabilities.setJavascriptEnabled(true);
			//capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,GlobalConstants.librariesPath +"phantomjs.exe");
			/* WebDriver driver= new PhantomJSDriver();
			*/
			try{
				
				/*if(GlobalConstants.platform.equalsIgnoreCase("Windows")){
					System.setProperty("webdriver.chrome.driver",GlobalConstants.librariesPath +"chromedriver.exe"); 			
				
				}else{
					System.setProperty("webdriver.chrome.driver",GlobalConstants.librariesPath +"chromedriver");
				}*/
			driver=new HtmlUnitDriver();
				
			//driver= new PhantomJSDriver(capabilities);
				
			//System.out.println("After PJS");
			driver.manage().window().maximize();
			//System.out.println("***HTML UNIT DRIVER STARTED");
			
			}catch(Throwable e){e.printStackTrace();}
		}	
		
		
		
		driver.manage().timeouts().implicitlyWait(GlobalConstants.implicitWaitTime, TimeUnit.SECONDS);		
		return driver;
	}
	
	public  static SelendroidDriver getSelendroidMobileDriver() throws Exception{				
		
		/*SelendroidConfiguration config = new SelendroidConfiguration();		
		File app = new File(GlobalConstants.mobileAPKFilePath, GlobalConstants.mobileAppAPKName);		
		config.addSupportedApp(app.getAbsolutePath());
		GlobalConstants.selendroidServer = new SelendroidLauncher(config);
		GlobalConstants.selendroidServer.launchSelendroid();*/
		
		SelendroidDriver driver=null;
		
		/*if(automationType.equalsIgnoreCase("Mobile App")){	
			
			
			
			SelendroidCapabilities cap=new SelendroidCapabilities(GlobalConstants.mobileAppID);
			if (GlobalConstants.mobileExecutionMode.equalsIgnoreCase("Emulator"))
					cap.setEmulator(true);
					
			else{
				
				cap.setEmulator(true);
			
			cap.setModel(GlobalConstants.mobileDeviceName);				
			cap.setBrowserName(GlobalConstants.mobileBrowser);		
			
			cap.setAut(GlobalConstants.mobileAppID);
			cap.setLaunchActivity(GlobalConstants.mobileAppActivity);
			}
			driver=new SelendroidDriver(new SelendroidCapabilities(GlobalConstants.mobileAppID));
			
			
		}else{*/
			
			SelendroidCapabilities cap=new SelendroidCapabilities();
			if (GlobalConstants.mobileExecutionMode.equalsIgnoreCase("Emulator"))
					cap.setEmulator(true);
			else{
				cap.setModel(GlobalConstants.mobileDeviceName);
				cap.setEmulator(false);
			}
				
			cap.setCapability(CapabilityType.BROWSER_NAME, GlobalConstants.mobileBrowser);
			cap.setCapability("deviceName", GlobalConstants.mobileDeviceName);
			cap.setCapability("platformVersion", GlobalConstants.mobilePlatformVersion);
			cap.setCapability("platformName", GlobalConstants.mobilePlatformName);
			
			cap.setCapability("appPackage", GlobalConstants.mobileAppPackage);
			cap.setCapability("appActivity", GlobalConstants.mobileAppActivity);
			
			//java.net.URL url=new java.net.URL("http://127.0.0.1:4723/wd/hub");
			driver=new SelendroidDriver(new SelendroidCapabilities(GlobalConstants.mobileAppID));
			
			
	//}
		driver.manage().timeouts().implicitlyWait(GlobalConstants.implicitWaitTime, TimeUnit.SECONDS);	
		//System.out.println("Selendroid driver initiated");
		
		return driver;			
		
		
	}
	
	public  static AppiumDriver getAppiumMobileDriver() throws Exception{
		
		DesiredCapabilities capabilities=new DesiredCapabilities();
		File app=null;
		//if (GlobalConstants.mobileExecutionMode.equalsIgnoreCase("Mobile Device")){				
			app = new File(GlobalConstants.mobileAPKFilePath, GlobalConstants.mobileAppAPKName); 
			capabilities.setCapability("app", app.getAbsolutePath());
		//}
		
		capabilities.setBrowserName(GlobalConstants.mobileBrowser);
		capabilities.setPlatform(Platform.ANDROID);
		
		
		//capabilities.setCapability(CapabilityType.BROWSER_NAME, GlobalConstants.mobileBrowser);
		capabilities.setCapability("deviceName", GlobalConstants.mobileDeviceName);
		capabilities.setCapability("platformVersion", GlobalConstants.mobilePlatformVersion);
		capabilities.setCapability("device", GlobalConstants.mobilePlatformName);
		capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
		
		//capabilities.setCapability("appPackage", GlobalConstants.mobileAppPackage);
		capabilities.setCapability("app-package", GlobalConstants.mobileAppPackage);
		capabilities.setCapability("app-activity", GlobalConstants.mobileAppActivity);
		//capabilities.setCapability("appWaitActivity", "com.dealertrack.ui.login.LoginActivity");
		
		//java.net.URL url=new java.net.URL("http://127.0.0.1:4723/wd/hub");
		java.net.URL url=new java.net.URL("http://"+GlobalConstants.gridIPAddressPort+"/wd/hub");
		
		
		AppiumDriver driver=null;
				
				if(GlobalConstants.mobile_appium_server_type.equalsIgnoreCase("Internal"))
					driver=new AndroidDriver<>(new URL(GlobalConstants.appiumServiceUrl), capabilities);		
					else
						driver=new AndroidDriver<>(url, capabilities);
		//System.out.println("Android driver initiated");
		
		driver.manage().timeouts().implicitlyWait(GlobalConstants.implicitWaitTime, TimeUnit.SECONDS);	
		
		return driver;			
		
		
	}
	
	
}

