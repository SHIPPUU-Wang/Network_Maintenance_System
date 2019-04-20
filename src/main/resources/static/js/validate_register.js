import Vue from 'node_modules/vee-validate/dist/vue'
import VeeValidate, {Validator} from 'node_modules/vee-validate/dist/vee-validate'
import zh from 'node_modules/vee-validate/dist/locale/zh_CN'

// 配置中文
Validator.addLocale(zh);

const config = {
	locale: 'zh_CN'
};

Vue.use(VeeValidate, config);

// 自定义validate
const dictionary = {
	zh_CN: {
		messages: {
			// email: () => '请输入正确的邮箱格式',
			password2: () => '请再次输入密码',
			required: (field) => "请输入" + field
		},
		attributes: {
			userName: '用户名',
			password: '密码',
			password2: '2密码',

		}
	}
};

Validator.updateDictionary(dictionary);

