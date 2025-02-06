function getFormattedDate() {
    const today = new Date();

    // 연, 월, 일 가져오기
    const year = today.getFullYear();
    const month = String(today.getMonth() + 1).padStart(2, '0');  // 월은 0부터 시작하므로 +1 필요
    const day = String(today.getDate()).padStart(2, '0');

    // 날짜 포맷 (예: 2025년 01월 10일)
    return `${year}년 ${month}월 ${day}일`;
}

// HTML 요소에 날짜 삽입
document.getElementById('current-date').innerText = getFormattedDate();