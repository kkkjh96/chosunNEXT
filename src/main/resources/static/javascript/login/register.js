let isEmailChecked = false; // 이메일 중복 검사 여부

function validateForm() {
    let isValid = true;

    // 입력 필드 가져오기
    const emailField = document.getElementById('email');
    const passwordField = document.getElementById('password');
    const confirmPasswordField = document.getElementById('confirm-password');
    const nicknameField = document.getElementById('nickname');

    // 에러 메시지 초기화
    clearErrors();

    // 이메일 유효성 검사
    if (!validateEmail(emailField.value.trim())) {
        showError(emailField, '유효한 이메일 주소를 입력하세요.', 'email-error');
        isValid = false;
    }

    // 이메일 중복 검사 여부 확인
    if (!isEmailChecked) {
        showError(emailField, '이메일 중복 검사를 진행하세요.', 'email-error');
        isValid = false;
    }

    // 비밀번호 유효성 검사
    if (passwordField.value.length < 8 || !/[a-z]/.test(passwordField.value) || !/[0-9]/.test(passwordField.value)) {
        showError(passwordField, '비밀번호는 영문 소문자, 숫자 1자 이상을 포함하고 8자 이상이어야 합니다.', 'password-error');
        isValid = false;
    }

    // 비밀번호 확인 검사
    if (passwordField.value !== confirmPasswordField.value) {
        showError(confirmPasswordField, '비밀번호가 일치하지 않습니다.', 'confirm-password-error');
        isValid = false;
    }

    // 별명 유효성 검사
    if (nicknameField.value.trim().length === 0) {
        showError(nicknameField, '별명을 입력하세요.', 'nickname-error');
        isValid = false;
    }

    return isValid;
}

function validateEmail(email) {
    const emailRegex = /^[^@\s]+@[^@\s]+\.[^@\s]+$/;
    return emailRegex.test(email);
}

function showError(inputField, message, errorId) {
    const errorSpan = document.getElementById(errorId);
    errorSpan.textContent = message;
    errorSpan.style.display = 'block';
    inputField.classList.add('invalid');
}

function clearErrors() {
    const errorMessages = document.querySelectorAll('.error-message');
    const invalidInputs = document.querySelectorAll('input.invalid');

    errorMessages.forEach((error) => {
        error.textContent = '';
        error.style.display = 'none';
    });

    invalidInputs.forEach((input) => {
        input.classList.remove('invalid');
    });
}

// 이메일 중복 검사
function checkEmailDuplication() {
    const emailField = document.getElementById('email');
    const emailStatus = document.getElementById('email-status');

    // 이메일 형식 검증
    if (!validateEmail(emailField.value.trim())) {
        showError(emailField, '이메일 형식이 올바르지 않습니다.', 'email-error');
        return;
    }

    // AJAX 요청 (예시)
    api.post('/api/check-id', { id: emailField.value.trim() })
        .then(response => {
            console.log('이메일 중복 검사 성공:', response);
            if (response) {
                emailStatus.textContent = '이미 사용 중인 이메일입니다.';
                emailStatus.style.color = 'red';
                emailStatus.style.display = 'block';
                isEmailChecked = false;
            } else {
                emailStatus.textContent = '사용 가능한 이메일입니다.';
                emailStatus.style.color = 'green';
                emailStatus.style.display = 'block';
                isEmailChecked = true;
            }
        })
        .catch(error => {
            console.error('이메일 중복 검사 오류:', error);
            emailStatus.textContent = '중복 검사 중 오류가 발생했습니다.';
            emailStatus.style.color = 'red';
            emailStatus.style.display = 'block';
        });

    // 이메일 변경 시 다시 중복 검사 안내
    emailField.addEventListener('input', () => {
        isEmailChecked = false;
        emailStatus.textContent = '이메일을 변경했습니다. 다시 중복 검사를 진행하세요.';
        emailStatus.style.color = 'orange';
        emailStatus.style.display = 'block';
    });
}

function submitSignupForm() {
    const email = document.getElementById('email').value.trim();
    const password = document.getElementById('password').value.trim();
    const nickname = document.getElementById('nickname').value.trim();

    api.post('/api/signup', {
        id: email,
        password: password,
        nickname: nickname
    })
        .then(response => {
            console.log('회원가입 성공:', response);
            alert(response); // "회원가입이 완료되었습니다."
            window.location.href = '/login'; // 회원가입 완료 후 로그인 페이지로 이동
        })
        .catch(error => {
            console.error('회원가입 실패:', error);
            alert('회원가입 중 오류가 발생했습니다.');
        });
}

// 유효성 검사가 통과되면 submitSignupForm() 호출
document.getElementById('signup-form').addEventListener('submit', function (event) {
    event.preventDefault(); // 폼 기본 동작 방지

    if (validateForm()) {
        submitSignupForm(); // 유효성 검사가 통과되면 회원가입 요청 실행
    }
});
