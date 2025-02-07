document.addEventListener('DOMContentLoaded', function () {
    const path = window.location.pathname; // "/survey/view/1"
    const surveyId = path.split('/').pop(); // "1"
    console.log(surveyId);


    // 설문 데이터 불러오기
    api.get(`/api/survey/${surveyId}`)
        .then(response => {
            console.log(response);  // 설문 데이터
            renderSurveyForm(response);
        })
        .catch(error => {
            console.error('설문 데이터를 가져오는데 실패했습니다.', error);
            alert('설문 데이터를 가져오는데 오류가 발생했습니다.');
        });

    // 설문 폼 렌더링
    function renderSurveyForm(survey) {
        document.querySelector('#surveyTitle').value = survey.title;
        document.querySelector('#surveyDescription').value = survey.content;

        survey.questions.forEach((question, index) => {
            addQuestionElement(index + 1, question);
        });
    }

    let questionIndex = 0;

    // 질문 추가
    document.getElementById('addQuestion').addEventListener('click', function () {
        addQuestionElement(++questionIndex);
    });

    function addQuestionElement(index, questionData = null) {
        const questionContainer = document.createElement('div');
        questionContainer.classList.add('form-group', 'question-container');

        questionContainer.innerHTML = `
            <div class="question-header">
                <label class="label-text question-label">질문 ${index}:</label>
                <span class="delete-question-icon">&times;</span>
            </div>
            <input type="text" class="input-field question-content" value="${questionData ? questionData.content : ''}">
            <label class="label-text">질문 유형:</label>
            <select class="input-field select-field">
                <option value="">--선택--</option>
                <option value="SINGLE_CHOICE" ${questionData && questionData.type === 'SINGLE_CHOICE' ? 'selected' : ''}>단일 선택</option>
                <option value="MULTIPLE_CHOICE" ${questionData && questionData.type === 'MULTIPLE_CHOICE' ? 'selected' : ''}>다중 선택</option>
                <option value="TEXT" ${questionData && questionData.type === 'TEXT' ? 'selected' : ''}>주관식</option>
            </select>
            <div class="form-group options-container"></div>
            <button type="button" class="button add-option">옵션 추가</button>
        `;

        document.getElementById('questions').appendChild(questionContainer);

        // 질문 삭제 이벤트 등록
        questionContainer.querySelector('.delete-question-icon').addEventListener('click', function () {
            questionContainer.remove();
            updateQuestionNumbers();
        });

        // 옵션 추가 이벤트 등록
        questionContainer.querySelector('.add-option').addEventListener('click', function () {
            addOptionElement(questionContainer.querySelector('.options-container'), questionData ? questionData.options.length : 0);
        });

        // 기존 옵션 추가 (수정 시)
        if (questionData && questionData.options) {
            questionData.options.forEach(option => {
                addOptionElement(questionContainer.querySelector('.options-container'), null, option);
            });
        }
    }

    function addOptionElement(optionsContainer, optionIndex = 0, optionData = null) {
        const optionItem = document.createElement('div');
        optionItem.classList.add('option-item');

        optionItem.innerHTML = `
            <div class="option-wrapper">
                <input type="text" class="input-field option-content" value="${optionData ? optionData.content : ''}">
                <span class="delete-option-icon">&times;</span>
            </div>
        `;

        optionsContainer.appendChild(optionItem);

        // 옵션 삭제 이벤트 등록
        optionItem.querySelector('.delete-option-icon').addEventListener('click', function () {
            optionItem.remove();
        });

        // 새 옵션 입력 필드에 포커스 이동
        optionItem.querySelector('input').focus();
    }

    // 질문 번호 업데이트 함수
    function updateQuestionNumbers() {
        const questionContainers = document.querySelectorAll('.question-container');
        questionIndex = questionContainers.length;

        questionContainers.forEach((container, index) => {
            container.querySelector('.question-label').textContent = `질문 ${index + 1}:`;
        });
    }

    // 설문조사 저장
    document.getElementById('submitSurvey').addEventListener('click', function () {
        const surveyData = {
            title: document.querySelector('#surveyTitle').value.trim(),
            description: document.querySelector('#surveyDescription').value.trim(),
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

        // 질문 및 옵션 데이터 수집
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

            // 옵션 수집
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

        // 서버에 수정 요청 전송
        api.put(`/api/survey/${surveyId}`, surveyData)
            .then(() => {
                alert('설문조사가 성공적으로 수정되었습니다!');
                window.location.href = '/survey/list';
            })
            .catch(error => {
                console.error('설문조사 수정에 실패했습니다.', error);
                alert('설문조사 수정에 실패했습니다.');
            });
    });
});
