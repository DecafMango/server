package dragon;

import java.io.Serializable;

public class DragonCave implements Serializable {
    private Integer depth; //Поле не может быть null

    public DragonCave(Integer depth) {
        this.depth = depth;
    }
    public Integer getDepth() {
        return depth;
    }
}
