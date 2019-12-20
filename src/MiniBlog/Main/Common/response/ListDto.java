package MiniBlog.Main.Common.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListDto implements Serializable {
    private static final long serialVersionUID = -3809382578233943933L;

    public static final String YES = "yes";
    public static final String NO = "no";

    // 分页数据
    private List list;
    // 是否还有数据的标志{yes : 这是最后一页, no : 这不是最后一页}
    private String noMore;

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public String getNoMore() {
        return noMore;
    }

    public void setNoMore(String noMore) {
        this.noMore = noMore;
    }

    public ListDto(List list, Integer expectPage) {
        if (Objects.isNull(list)) {
            list = new ArrayList(0);
        }
        this.list = list;
        if (list.size() <= expectPage) {
            this.noMore = YES;
        } else {
            this.noMore = NO;
            this.list.remove(expectPage.intValue());
        }
    }

}
