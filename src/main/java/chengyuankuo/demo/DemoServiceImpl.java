package chengyuankuo.demo;

import org.apache.thrift.TException;

import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

/**
 * @author chengyuankuo
 */
@Slf4j
public class DemoServiceImpl implements DemoService.Iface{
    private Map<String,Integer> name2age = new HashMap<>();

    public DemoServiceImpl() {
        name2age.put("小明",23);
        name2age.put("小红",18);
    }

    @Override
    public int getAge(String name) throws TException {
        log.info("name={}",name);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return name2age.get(name);
    }
}
