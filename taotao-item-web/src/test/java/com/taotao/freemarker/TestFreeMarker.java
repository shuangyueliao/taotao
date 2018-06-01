package com.taotao.freemarker;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class TestFreeMarker {
	@Test
	public void testFreemarker()throws Exception{
		Configuration configuration=new Configuration(Configuration.getVersion());
		configuration.setDirectoryForTemplateLoading(new File("F:/workspace/taotao-item-web/src/main/webapp/WEB-INF/ftl"));
		configuration.setDefaultEncoding("utf-8");
		Template template = configuration.getTemplate("student.ftl");
		Map data=new HashMap<>();
		data.put("hello", "hello freemarker");
		Student student=new Student(1,"小明",11,"北京");
		data.put("student", student);
		
		List<Student>stuList=new ArrayList<>();
		stuList.add(new Student(1,"小明1",11,"北京"));
		stuList.add(new Student(2,"小明2",12,"北京"));
		stuList.add(new Student(3,"小明3",13,"北京"));
		stuList.add(new Student(4,"小明4",14,"北京"));
		stuList.add(new Student(5,"小明5",15,"北京"));
		stuList.add(new Student(6,"小明6",16,"北京"));
		stuList.add(new Student(7,"小明7",17,"北京"));
		data.put("stuList", stuList);
		
		data.put("date", new Date());
		
		Writer out=new FileWriter(new File("C:/Users/John/Desktop/student.html"));
		template.process(data, out);
		out.close();
	}
}
