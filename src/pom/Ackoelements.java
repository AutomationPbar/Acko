package pom;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Ackoelements {
	
	public static WebElement dontknow(WebDriver driver){
		
		WebElement dontknow = driver.findElement(By.id("selctVehicleId"));
		return dontknow;
		
	}
	
	public static WebElement selectmake(WebDriver driver){
		
		WebElement selectmake = driver.findElement(By.xpath(".//*[@id='selectModelId']"));
		return selectmake;
		
	}
	
	public static WebElement selectmodell(WebDriver driver,int m){
		
		WebElement selectmodel = driver.findElement(By.id("modelOption_"+m));
		return selectmodel;
		
	}
	public static WebElement selectmodel(WebDriver driver){
		
		WebElement selectmodel = driver.findElement(By.id("modelOption_0"));
		return selectmodel;
		
	}

	public static List<WebElement> variantsCount(WebDriver driver){
		
		List<WebElement> value = driver.findElements(By.xpath(".//*[@class='DownPart']/div[1]/div[1]/ul[1]/li"));
		return value;
		
	}

	public static WebElement variants(WebDriver driver, int i){
		
		WebElement value = driver.findElement(By.xpath(".//*[@class='DownPart']/div[1]/div[1]/ul[1]/li[" + i + "]"));
		return value;
		
	}

	public static WebElement insuredvalue(WebDriver driver){
	
	WebElement value = driver.findElement(By.className("InsuredValuePrice"));
	return value;
	
}
	

	public static WebElement carprice(WebDriver driver){
	
	WebElement value = driver.findElement(By.className("CarPriceLabel"));
	return value;
	
}
	
	public static WebElement basevalue(WebDriver driver){
		
		WebElement value = driver.findElement(By.xpath("//*[@class='Even Selected']"));
		return value;
		
	}
	
	public static WebElement crossbutton(WebDriver driver){
		
		WebElement value = driver.findElement(By.xpath("//*[@class='SimpleButtonWrap' and @id='selectVariantIdCrossId']"));
		return value;
		
	}
	
	public static WebElement claimyear(WebDriver driver){
		
		WebElement value = driver.findElement(By.id("lastclaimYear_0"));
		return value;
		
	}
	
	public static WebElement odometer(WebDriver driver){
		
		WebElement value = driver.findElement(By.id("radioOption_0-5"));
		return value;
		
	}
	public static WebElement agecheck(WebDriver driver){
	
	WebElement value = driver.findElement(By.id("radioOption_0-25"));
	return value;
	
}
	
	public static WebElement regyear(WebDriver driver,int y){
		
		String selid = "SelectRegYear_"+y;
		
		WebElement value = driver.findElement(By.xpath(".//*[@id='"+selid+"']"));
		System.out.println("value of id for year is  " + selid);
		return value;
		
	}
	
public static WebElement claimregyear(WebDriver driver,int y){
		
		String selid = "lastclaimYear_"+y;
		
		WebElement value = driver.findElement(By.xpath(".//*[@id='"+selid+"']"));
		System.out.println("value of id for year is  " + selid);
		return value;
		
	}

public static WebElement buymonth(WebDriver driver,int y){
	
	String month = "selectRegMonth_"+y;
	
	WebElement value = driver.findElement(By.xpath("//*[@id='"+month+"']"));
	System.out.println("value of id for month is  " + month);
	return value;
	
}

public static WebElement addon(WebDriver driver){
	
	
	
	WebElement value = driver.findElement(By.xpath("//*[@class='ZeroDepPrice']"));
	
	return value;
	
}
	
	public static WebElement thirdcontinuebtn(WebDriver driver){
		
		WebElement value = driver.findElement(By.xpath("//a[@href='/lastclaimyear/?quote=3rhi6Ja3d2y_0GcbacXNog']"));
		return value;
		
	}
	
public static WebElement previouspolicyexpiry(WebDriver driver){
		
		WebElement value = driver.findElement(By.id("pevPoliceExpOptId_0-10"));
		return value;
		
	}

public static WebElement secondcontinuebtn(WebDriver driver){
	
	WebElement value = driver.findElement(By.xpath("//a[@href='/registrationyear/?quote=3rhi6Ja3d2y_0GcbacXNog' and @class='HoverEffect GreenLinkWrap']"));
	return value;
	
}
public static WebElement firstcontinuebtn(WebDriver driver){
	
	WebElement value = driver.findElement(By.xpath("//.[@class='HoverEffect GreenLinkWrap']"));
	return value;
	
}
	public static WebElement policyexpiry(WebDriver driver){
	
	WebElement value = driver.findElement(By.id("previousPolicyStausId_true"));
	return value;
	
	}
	
	public static WebElement pincontinue(WebDriver driver){
		
		WebElement value = driver.findElement(By.id("pincodeContinueClickId"));
		return value;
		
		}
	
	public static WebElement pincode(WebDriver driver){
		
		WebElement value = driver.findElement(By.id("pinInputId"));
		return value;
		
		}
	public static WebElement carcontinue(WebDriver driver){
		
		WebElement value = driver.findElement(By.id("continueToPincodeId"));
		return value;
		
		}
	
	public static WebElement petrol(WebDriver driver){
		
		WebElement value = driver.findElement(By.id("fueltypeId0"));
		return value;
		
		}
	public static WebElement diesel(WebDriver driver){
		
		WebElement value = driver.findElement(By.id("fueltypeId1"));
		return value;
		
		}
	
public static WebElement editlink(WebDriver driver){
		
		WebElement edit = driver.findElement(By.id("editLink"));
		return edit;
		
	}

public static WebElement edityear(WebDriver driver){
	
	WebElement edit = driver.findElement(By.xpath("//*[@id='boughtInYear']"));
	return edit;
	
}
public static WebElement editpincode(WebDriver driver){
	
	WebElement edit = driver.findElement(By.xpath("//*[@id='returnToPincode']"));
	return edit;
	
}
public static WebElement modelcrossbutton(WebDriver driver){
	
	WebElement value = driver.findElement(By.xpath("//*[@class='SimpleButtonWrap' and @id='//*[@id='selectModelIdCrossId']']"));
	return value;
	
}
}
