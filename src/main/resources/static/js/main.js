document.addEventListener("DOMContentLoaded", () => {
    const stories = document.querySelectorAll('.story');
    const modal = document.getElementById('modal');
    const modalText = document.getElementById('modal-text');
    const closeButton = document.querySelector('.close-button');

    stories.forEach(story => {
        story.addEventListener('click', () => {
            const storyContent = story.getAttribute('data-story');
            modalText.textContent = storyContent;
            modal.style.display = 'block';
        });
    });

    closeButton.addEventListener('click', () => {
        modal.style.display = 'none';
    });

    window.addEventListener('click', (event) => {
        if (event.target === modal) {
            modal.style.display = 'none';
        }
    });
});
