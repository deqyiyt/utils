package com.ias.common.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.ias.common.utils.id.UUIDUtils;
import com.ias.common.utils.json.JsonUtil;
import com.ias.common.utils.random.RandomUtils;

public class JsonTest {
    
    private BeanTest createBean() {
        return new BeanTest(UUIDUtils.createSystemDataPrimaryKey(), RandomUtils.getRandomValue(8));
    }
    
    /**
     * 对象转json
     * 对象表示所有java对象，包括list，map，javabean等
     * @author jiuzhou.hu
     * @date 2017年11月1日 上午10:33:06
     */
    @Test
    public void objectToJsonTest() {
        BeanTest bean = createBean();
        bean.addChild(createBean());
        bean.addChild(createBean());
        bean.addChild(createBean());
        bean.addChild(createBean());
        System.out.println(JsonUtil.buildNormalBinder().toJson(bean));
    }
    
    /**
     * json转对象
     * @author jiuzhou.hu
     * @date 2017年11月1日 上午10:33:06
     */
    @Test
    public void jsonToObjectTest() {
        String json = "{\"id\":\"ed4618ee4adb4e14aa43275e0afa03ea\",\"value\":\"3PcUr3id\",\"child\":null}";
        BeanTest bean = JsonUtil.buildNormalBinder().getJsonToObject(json, BeanTest.class);
        System.out.println(bean.getId());
    }
    
    /**
     * json转list对象
     * @author jiuzhou.hu
     * @date 2017年11月1日 上午10:33:06
     */
    @Test
    public void jsonToListTest() {
        String json = "[{\"id\":\"6eb1bbf9a46d46e8b98645e5ac9cc716\",\"value\":\"G28kxDrt\",\"child\":null},{\"id\":\"9cb3cef203f54e91bf0a4fcb37ad3bdc\",\"value\":\"bhGeg3qL\",\"child\":null},{\"id\":\"604f59b1fbb244d49047df4b27f78153\",\"value\":\"HqwkpIvH\",\"child\":null},{\"id\":\"70ff92f47f7c4c489c3d89f358f62500\",\"value\":\"EO7xCE3L\",\"child\":null},{\"id\":\"a9910838a979442da46ff5ba8f873f21\",\"value\":\"Fn1N3kqb\",\"child\":null},{\"id\":\"ad307099b8234a609f2427f07a868275\",\"value\":\"z3D94dm7\",\"child\":null},{\"id\":\"14a5408d541e4eccacb07966d7a5b04c\",\"value\":\"ysYOUPIV\",\"child\":null},{\"id\":\"b814b17d91574fb3b7f1e45ae163e208\",\"value\":\"h1K5wLxe\",\"child\":null}]";
        List<BeanTest> list = JsonUtil.buildNormalBinder().getJsonToList(json, BeanTest.class);
        System.out.println(list.get(0).getId());
    }
    
    /**
     * json转map
     * @author jiuzhou.hu
     * @date 2017年11月1日 上午10:33:06
     */
    @Test
    public void jsonToMapTest() {
        String json = "{\"0d2daf7870684915a56f6d948716d959\":{\"id\":\"32f5830026c048d683faf960b841b46c\",\"value\":\"9VkSWKBm\",\"child\":null},\"a8bae9b2efda49c5a393824dd0a56175\":{\"id\":\"ef8544b0ac024865925fb5d239551917\",\"value\":\"D2CYoABp\",\"child\":null},\"4969614ed97b4ba988a71e07811fb87b\":{\"id\":\"30a0503f5aed44bb9c7f1cad558b7a08\",\"value\":\"lUoC7Y7a\",\"child\":null},\"3b29299ff8fa49b3ae96f0f37473fd00\":{\"id\":\"35c76d45edd347038f046b84449fd633\",\"value\":\"CT6G2g7I\",\"child\":null},\"10b8a71a7a3540f9a7bbb7dc2872b922\":{\"id\":\"4b4a417c17354e599aafbd3333ad77e7\",\"value\":\"qB3DNJK8\",\"child\":null},\"7bd759c79dd648ec85149e5761445eac\":{\"id\":\"57c872c272934992b3536d3d0f941146\",\"value\":\"y0C21dPN\",\"child\":null}}";
        Map<String, BeanTest> map = JsonUtil.buildNormalBinder().getJsonToMap(json, String.class, BeanTest.class);
        System.out.println(map.get("0d2daf7870684915a56f6d948716d959").getId());
    }
}

class BeanTest {
    private String id;
    private String value;
    private List<BeanTest> child;
    
    public BeanTest() {
        super();
    }

    public BeanTest(String id, String value) {
        super();
        this.id = id;
        this.value = value;
    }
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    public List<BeanTest> getChild() {
        return child;
    }
    public void setChild(List<BeanTest> child) {
        this.child = child;
    }
    
    public void addChild(BeanTest bean) {
        if(this.child == null) {
            this.child = new ArrayList<>();
        }
        child.add(bean);
    }
    
}
