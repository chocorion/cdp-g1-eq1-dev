context('Assertions', () => {
    beforeEach(() => {
        cy.visit('http://localhost:8080');
    });

    it('Should open and close popup', () => {
        cy.get('.add-project-button').click();
        cy.wait(500);
        cy.get('.project-modal').should('be.visible');
        cy.get('.close-project-modal').click();
        cy.wait(500);
        cy.get('.project-modal').should('not.be.visible');
    });

    it('Should add project', () => {
        const name = 'Cypress test project - name';
        const description = 'Cypress test project - description';
        cy.get('.add-project-button').click();
        cy.wait(500);
        cy.get('#name').type(name);
        cy.wait(500);
        cy.get('#description').type(description);
        cy.wait(1000);
        cy.get('.modal-submit-button').first().click();
        cy.wait(1000);
        cy.get('.project-modal').should('not.be.visible');

        cy.get('app-project-item')
            .last()
            .find('.projectName')
            .should('contain.text', name);

        cy.get('app-project-item')
            .last()
            .find('.projectInfo')
            .should('contain.text', description);
    });

    it('Should remove project', async () => {
        let lastProject = cy.get('app-project-item').last();
        lastProject.find('.projectName').invoke('text').then(
            name => {
                cy.get('app-project-item').last().find('.delete-button').click().wait(1000);

                cy.get('app-project-item')
                    .last()
                    .find('.projectName').wait(500).should('not.contain.text', name);
            }
        );
    });
});
