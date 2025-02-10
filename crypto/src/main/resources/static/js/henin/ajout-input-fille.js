function ajouterInputFille(containerName, childrenClass) {
    const container = document.getElementById(containerName);

    const currentIndex = container.children.length;

    // Clone the first sale detail block
    const newDetail = container.querySelector(childrenClass).cloneNode(true);

    // Update the th:field and other related attributes with the new index
    const inputs = newDetail.querySelectorAll('input, select');
    inputs.forEach(function (input) {
        // Update th:field for correct binding
        input.setAttribute('name', input.getAttribute('name').replace(/\[\d+]/, `[${currentIndex}]`));

        // Update the ids for uniqueness
        input.setAttribute('id', input.getAttribute('id').replace(/-\d+$/, `-${currentIndex}`));
    });

    // Clear input values in the new cloned block
    inputs.forEach(function (input) {
        input.value = '';
    });

    // Append the new detail to the container
    container.appendChild(newDetail);
}
