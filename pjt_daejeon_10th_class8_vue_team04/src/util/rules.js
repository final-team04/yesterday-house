export const userIdRule = [
  (userId) => !!userId || "아이디는 필수 입력 사항입니다.",
  (userId) => /^[a-zA-Z0-9]*$/.test(userId) || "아이디는 영문과 숫자만 입력할 수 있습니다.",
  (userId) =>
    !(userId && (userId.length < 8 || userId.length > 20)) ||
    "아이디는 8자 이상 20자 이하여야 합니다.",
];

export const userNameRule = [
  (userName) => !!userName || "닉네임은 필수 입력 사항입니다.",
  (userName) => /^[a-zA-Z0-9]*$/.test(userName) || "닉네임은 영문과 숫자만 입력할 수 있습니다.",
  (userName) =>
    !(userName && (userName.length < 8 || userName.length > 20)) ||
    "닉네임은 8자 이상 20자 이하여야 합니다.",
];

export const userPwRule = [
  (userPw) => !!userPw || "비밀번호는 필수 입력 사항입니다.",
  (userPw) => /^[a-zA-Z0-9]*$/.test(userPw) || "비밀번호는 영문과 숫자만 입력할 수 있습니다.",
  (userPw) =>
    !(userPw && (userPw.length < 8 || userPw.length > 20)) ||
    "비밀번호는 8자 이상 20자 이하여야 합니다.",
];

export const userPwCheckRule = [
  (userPwCheck) => !!userPwCheck || "비밀번호 확인은 필수 입력 사항입니다.",
  (userPwCheck) =>
    /^[a-zA-Z0-9]*$/.test(userPwCheck) || "비밀번호 확인은 영문과 숫자만 입력할 수 있습니다.",
  (userPwCheck) =>
    !(userPwCheck && (userPwCheck.length < 8 || userPwCheck.length > 20)) ||
    "비밀번호 확인은 8자 이상 20자 이하여야 합니다.",
];

/*
GET path params

const route = useRoute();
const keyword = ref(route.params.keyword);

*/
