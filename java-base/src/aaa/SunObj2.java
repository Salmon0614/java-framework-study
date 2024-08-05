package aaa;

import bbb.FatherObj;

/**
 * @author Salmon
 * @since 2024-08-04
 */
public class SunObj2 extends FatherObj {

    private Integer a;

    public Integer getA() {
        return a;
    }

    public void setA(Integer a) {
        this.a = a;
    }

    @Override
    public void aa() {
        System.out.println("aaaaaa");
    }

//    @Override
//    void bb() {
//        super.bb();
//    }

    @Override
    protected void cc() {
        super.cc();
    }
}
