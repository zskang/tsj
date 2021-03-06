jQuery.extend(jQuery.validator.messages, {
  required: "必填",
  remote: "已存在!",
  email: "电子邮件格式错误",
  url: "网址格式错误",
  date: "日期格式错误",
  dateISO: "请输入合法的日期 (ISO).",
  number: "请输入合法的数字",
  digits: "只能输入整数",
  creditcard: "请输入合法的信用卡号",
  equalTo: "请再次输入相同的值",
  accept: "请输入拥有合法后缀名 的字符串",
  cnCharset:"请输入中文",
  maxlength: jQuery.validator.format("不得大于 {0} 个字符"),
  minlength: jQuery.validator.format("不能少于 {0} 个字符"),
  rangelength: jQuery.validator.format("请输入 一个长度介于 {0} 和 {1} 之间的字符串"),
  range: jQuery.validator.format("请输入一个介于 {0} 和 {1} 之间的值"),
  max: jQuery.validator.format("请输入一个最大为{0} 的值"),
  min: jQuery.validator.format("请输入一个最小为{0} 的值")
});