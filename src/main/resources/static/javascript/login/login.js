/**
 * 로그인 폼 유효성 검사 함수
 */
function validateLoginForm() {
    const email = document.getElementById('login-email').value.trim();
    const password = document.getElementById('login-password').value.trim();

    let isValid = true;

    // 이메일 입력 확인
    if (!email) {
        document.getElementById('login-email-error').innerText = '이메일을 입력해주세요.';
        isValid = false;
    } else {
        document.getElementById('login-email-error').innerText = '';
    }

    // 비밀번호 입력 확인
    if (!password) {
        document.getElementById('login-password-error').innerText = '비밀번호를 입력해주세요.';
        isValid = false;
    } else {
        document.getElementById('login-password-error').innerText = '';
    }

    return isValid;
}

/**
 * 로그인 폼 제출 이벤트 처리
 */
document.getElementById('login-form').addEventListener('submit', function (event) {
    event.preventDefault();  // 기본 폼 제출 방지

    // 유효성 검사 실패 시 리턴
    if (!validateLoginForm()) {
        return;
    }

    // Spring Security 기본 폼 로그인 방식 사용
    const form = document.getElementById('login-form');
    form.submit();  // 폼을 전송하여 /login 경로로 로그인 요청
});
