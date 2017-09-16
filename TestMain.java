import java.util.ArrayList;
import java.util.List;

public class TestMain {

public static void main(String[] args) {
		HTMLTemplate bean  = new HTMLTemplate();
		bean.addTag("tr");
		List<HTMLTemplate> list = new ArrayList<HTMLTemplate>();
		list.add(new HTMLTemplate().addTag("button").addTagId("ottinput").addTagName("ceshi").addTagClass("md-col-12","abcd").addTagContext("按钮"));
		list.add(new HTMLTemplate().addTag("button").addTagId("ottinput").addTagName("ceshi").addTagClass("md-col-12","abcd").addTagContext("按钮"));
		list.add(new HTMLTemplate().addTag("button").addTagId("ottinput").addTagName("ceshi").addTagClass("md-col-12","abcd").addTagContext("按钮"));
		//bean.addTag("button").addTagId("ottinput").addTagName("ceshi").addTagClass("md-col-12","abcd").addTagContext("按钮");
		bean.addTagElementList(list);
		System.out.println(bean.getHTMLTemplate());
	}


}


/*

<tr>
<button id='ottinput' name='ceshi' class=' md-col-12  abcd   '>
按钮</button>
<button id='ottinput' name='ceshi' class=' md-col-12  abcd   '>
按钮</button>
<button id='ottinput' name='ceshi' class=' md-col-12  abcd   '>
按钮</button>
</tr>


*/
