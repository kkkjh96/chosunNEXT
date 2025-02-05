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
document.getElementById('login-form').addEventListener('submit', async function (event) {
    event.preventDefault();  // 기본 폼 제출 방지

    // 유효성 검사 실패 시 리턴
    if (!validateLoginForm()) {
        return;
    }

    try {
        // 폼 데이터를 객체로 구성
        const loginRequest = {
            userId: document.getElementById('login-email').value.trim(),
            password: document.getElementById('login-password').value.trim()
        };

        // 서버에 로그인 요청 보내기
        const response = await axios.post('/api/login', loginRequest);

        if (response.status === 200) {
            // 로그인 성공 시 처리
            alert('로그인에 성공했습니다.');
            window.location.href = '/';  // 메인 페이지로 리다이렉트
        }
    } catch (error) {
        // 로그인 실패 시 에러 메시지 표시
        if (error.response && error.response.status === 401) {
            document.getElementById('login-password-error').innerText = '이메일 또는 비밀번호가 잘못되었습니다.';
        } else {
            alert('로그인 중 오류가 발생했습니다.');
        }
    }
});
