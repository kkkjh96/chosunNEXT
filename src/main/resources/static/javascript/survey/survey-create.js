let questionIndex = 0;

document.getElementById('addQuestion').addEventListener('click', function () {
    questionIndex++;

    const questionContainer = document.createElement('div');
    questionContainer.classList.add('form-group', 'question-container');

    questionContainer.innerHTML = `
        <div class="question-header">
            <label class="label-text question-label">질문 ${questionIndex}:</label>
            <span class="delete-question-icon">&times;</span>
        </div>
        <input type="text" name="questions[${questionIndex}].content" class="input-field question-content">

        <label class="label-text">질문 유형:</label>
        <select name="questions[${questionIndex}].type" class="input-field select-field">
            <option value="">--선택--</option>
            <option value="SINGLE_CHOICE">단일 선택</option>
            <option value="MULTIPLE_CHOICE">다중 선택</option>
            <option value="TEXT">주관식</option>
        </select>

        <div class="form-group options-container"></div>

        <button type="button" class="button add-option">옵션 추가</button>
    `;

    // 질문 컨테이너 추가
    document.getElementById('questions').appendChild(questionContainer);

    // 포커스 및 스크롤 이동 적용
    const questionContentInput = questionContainer.querySelector('.question-content');
    questionContentInput.focus();
    questionContainer.scrollIntoView({ behavior: 'smooth', block: 'center' });

    // 질문 삭제 이벤트 등록
    questionContainer.querySelector('.delete-question-icon').addEventListener('click', function () {
        questionContainer.remove();
        updateQuestionNumbers();
    });

    // 옵션 추가 이벤트 등록
    questionContainer.querySelector('.add-option').addEventListener('click', function () {
        const optionsContainer = questionContainer.querySelector('.options-container');
        const optionCount = optionsContainer.querySelectorAll('.option-item').length;

        const newOptionDiv = document.createElement('div');
        newOptionDiv.classList.add('option-item');

        newOptionDiv.innerHTML = `
            <div class="option-wrapper">
                <input type="text" name="questions[${questionIndex}].options[${optionCount}].content" class="input-field option-content">
                <span class="delete-option-icon">&times;</span>
            </div>
        `;

        optionsContainer.appendChild(newOptionDiv);

        // 옵션 삭제 이벤트 등록
        newOptionDiv.querySelector('.delete-option-icon').addEventListener('click', function () {
            newOptionDiv.remove();
        });

        // 포커스 이동 및 스크롤 이동
        const newOptionInput = newOptionDiv.querySelector('input');
        newOptionInput.focus();
        newOptionInput.scrollIntoView({ behavior: 'smooth', block: 'center' });
    });
});

// 질문 번호 업데이트 함수
function updateQuestionNumbers() {
    const questionContainers = document.querySelectorAll('.question-container');
    questionIndex = questionContainers.length;

    questionContainers.forEach((container, index) => {
        const label = container.querySelector('.question-label');
        label.textContent = `질문 ${index + 1}:`;

        // 이름 속성도 새 인덱스에 맞게 업데이트
        container.querySelector('.question-content').name = `questions[${index + 1}].content`;
        container.querySelector('.select-field').name = `questions[${index + 1}].type`;

        const options = container.querySelectorAll('.option-item input');
        options.forEach((option, optIndex) => {
            option.name = `questions[${index + 1}].options[${optIndex}].content`;
        });
    });
}

document.getElementById('submitSurvey').addEventListener('click', function () {
    const surveyData = {
        title: document.querySelector('#surveyTitle').value.trim(),
        content: document.querySelector('#surveyDescription').value.trim(),
        questions: []
    };

    let isValid = true;
    const validationMessages = [];

    const surveyTitle = document.querySelector('#surveyTitle').value.trim();
    const surveyDescription = document.querySelector('#surveyDescription').value.trim();

    if(!surveyTitle){
        isValid = false;
        validationMessages.push('설문 제목이 비어 있습니다.');
    }

    if(!surveyDescription){
        isValid = false;
        validationMessages.push('설문 내용이 비어 있습니다.');
    }

    // 질문 컨테이너들 순회하며 데이터 수집 및 유효성 검사
    document.querySelectorAll('.question-container').forEach((questionContainer, questionIndex) => {
        const questionContent = questionContainer.querySelector('.question-content').value.trim();
        const questionType = questionContainer.querySelector('.select-field').value;

        if (!questionContent) {
            isValid = false;
            validationMessages.push(`질문 ${questionIndex + 1}의 내용이 비어 있습니다.`);
        }

        if (!questionType) {
            isValid = false;
            validationMessages.push(`질문 ${questionIndex + 1}의 유형이 선택되지 않았습니다.`);
        }

        const questionData = {
            content: questionContent,
            type: questionType,
            options: []
        };

        // 옵션 데이터 수집
        questionContainer.querySelectorAll('.option-content').forEach(optionInput => {
            const optionContent = optionInput.value.trim();
            if (optionContent) {
                questionData.options.push({ content: optionContent });
            }
        });

        surveyData.questions.push(questionData);
    });

    // 유효성 검사 실패 시 경고 메시지 출력
    if (!isValid) {
        alert(`유효성 검사 실패:\n${validationMessages.join('\n')}`);
        return;
    }

    console.log(surveyData);  // JSON 데이터 확인용

    // 서버에 데이터 전송
    api.post('/api/survey', surveyData)
        .then(response => {
            alert('설문조사가 성공적으로 등록되었습니다!');
            window.location.href = '/survey/list';
        })
        .catch(error => {
            console.error(error);
            alert('설문조사 등록에 실패했습니다.');
        });
});

