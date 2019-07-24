package sun.com.annotation_compiler;

import com.google.auto.service.AutoService;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

import sun.com.annotations.BindPath;


/**
 * @author sunxiaoyun
 * @description $注解处理器
 * @time 19/7/19
 */
@AutoService(Processor.class) //注册注解处理器
public class AnnotationCompiler extends AbstractProcessor {
    //自动生成类对象
    Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        filer = processingEnvironment.getFiler();
    }

    /**
     * 声明注解处理器要处理的注解
     *
     * @return
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new HashSet<>();
        types.add(BindPath.class.getCanonicalName());
        return types;
    }

    /**
     * 声明注解处理器支持的SDK版本
     *
     * @return
     */
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return processingEnv.getSourceVersion();
    }

    /**
     * 注解处理器的核心方法,写文件
     *
     * @param set
     * @param roundEnvironment
     * @return
     */
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        //通过这个API拿到这个模块中所有用到bindpath的注解
        Set<? extends Element> elementsAnnotatedWith = roundEnvironment.getElementsAnnotatedWith(BindPath.class);
        //初始化数据
        Map<String, String> map = new HashMap<>();
        for (Element element : elementsAnnotatedWith) {
            TypeElement typeElement = (TypeElement) element;
            //获取到map中的key
            String key = typeElement.getAnnotation(BindPath.class).value();
            //获取带包名的类名
            String value = typeElement.getQualifiedName().toString();
            map.put(key, value);
        }
        if (map.size() > 0) {
            Writer writer = null;
            //创建类名
            String utilName = "ActivityUtil" + System.currentTimeMillis();
            try {
                JavaFileObject sourceFile = filer.createSourceFile("sun.com.util." + utilName);
                writer = sourceFile.openWriter();
                writer.write("package sun.com.util;\n" +
                        "\n" +
                        "import sun.com.arouter.ARouter;\n" +
                        "import sun.com.arouter.IRouter;\n" +
                        "\n" +
                        "public class " + utilName + " implements IRouter { \n" +
                        "@Override\n" +
                        "\tpublic void pushActivity() {\n");
                Iterator<String> iterator=map.keySet().iterator();
                while (iterator.hasNext()){
                    String key=iterator.next();
                    String value=map.get(key);
                    writer.write("\t\tARouter.getInstance().putActivity(\""+key+"\","+value+".class);\n");
                }
                writer.write("}\n}");
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
}
