package com.tipray.bean;

import java.io.Serializable;

/**
 * @author chenlong
 * @version 1.0 2018-07-31
 */
public class AppCenter implements Serializable {
    private static final long serialVersionUID = 1L;

    private long id;
    private String name;
    private String checked = "";

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        if (checked != null) {
            this.checked = "checked";
        }
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("AppCenter{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", checked='").append(checked).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
