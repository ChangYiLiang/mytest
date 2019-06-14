package com.chyl.mytest.builder;

import com.chyl.mytest.builder.model.Item;
import com.chyl.mytest.builder.model.MessageDataBean;
import com.chyl.mytest.builder.model.MessageTemplateBO;

/**
 * @Author: chyl
 * @Date: 2019/6/12 18:49
 */
public class MessageBuilderPro extends MessageTemplateBO {

    public MessageBuilderPro(Builder builder) {
        this.setTouser(builder.getTouser());
        this.setTemplate_id(builder.getTemplate_id());
        this.setUrl(builder.getUrl());
        this.setData(builder.getData());
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends MessageTemplateBO{

        public Builder touser(String touser) {
            this.setTouser(touser);
            return this;
        }

        public Builder template_id(String template_id) {
            this.setTemplate_id(template_id);
            return this;
        }

        public Builder url(String url) {
            this.setUrl(url);
            return this;
        }

        public Builder data(MessageDataBean messageDataBean) {
            this.setData(messageDataBean);
            return this;
        }

        public Builder messageDataBean(String... keyword) {
            MessageDataBean messageDataBean = new MessageDataBean();
            messageDataBean.setFirst(new Item(keyword[0]));
            messageDataBean.setKeyword1(new Item(keyword[1]));
            messageDataBean.setKeyword1(new Item(keyword[2]));
            messageDataBean.setKeyword1(new Item(keyword[3]));
            messageDataBean.setKeyword1(new Item(keyword[4]));
            messageDataBean.setKeyword1(new Item(keyword[5]));
            messageDataBean.setRemark(new Item(keyword[keyword.length - 1]));
            this.setData(messageDataBean);
            return this;
        }

        public MessageBuilderPro build() {
            return new MessageBuilderPro(this);
        }
    }

    public static void main(String[] args) {
        MessageBuilderPro messageBuilderPro = MessageBuilderPro.builder()
                .url("123")
                .touser("456")
                .template_id("789")
                .messageDataBean("a", "b", "c", "d", "e", "f")
                .build();
        System.out.printf(messageBuilderPro.toString());
    }
}
