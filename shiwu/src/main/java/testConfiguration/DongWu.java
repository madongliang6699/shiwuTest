package testConfiguration;

/**
 * @author ：madongliang
 * @date ：Created in 2019/8/20 16:39
 * @description： 格言：每天生活工作都要全力以赴，绝不偷懒！（尽量吧） 决定一个人的一生以及整个命运的，是日积月累的努力！
 */
public class DongWu {
    private String name;

    @Override
    public String toString() {
        return "DongWu{" +
                "name='" + name + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DongWu(String name) {

        this.name = name;
    }
}
