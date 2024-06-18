const fs = require('fs');
const path = require('path');

// Chemin du dossier contenant les modèles générés
const modelsDir = path.join(__dirname, '../projetFileRougeUi/src/app/dto/model');

// Supprimer les fichiers non désirés
const unwantedFiles = ['apiErrorResponse.ts', 'apiFieldError.ts', 'models.ts'];
unwantedFiles.forEach(file => {
    const filePath = path.join(modelsDir, file);
    if (fs.existsSync(filePath)) {
        fs.unlinkSync(filePath);
    }
});

// Renommer les fichiers DTO
fs.readdir(modelsDir, (err, files) => {
    if (err) {
        console.error('Erreur de lecture du dossier:', err);
        return;
    }

    files.forEach(file => {
        // Vérifier si le fichier est un DTO
        if (file.endsWith('DTO.ts')) {
            // Construire le chemin complet du fichier
            const filePath = path.join(modelsDir, file);
            const newFilePath = path.join(modelsDir, file.replace('DTO.ts', '.ts'));

            // Lire le contenu du fichier
            const content = fs.readFileSync(filePath, 'utf-8')
                .replace(/DTO/g, '');  // Retirer le "DTO" du contenu

            // Écrire le nouveau contenu dans un nouveau fichier sans "DTO" dans le nom
            fs.writeFileSync(newFilePath, content);
            // Supprimer l'ancien fichier
            fs.unlinkSync(filePath);
        }
    });
});

const directory = path.join(__dirname, '../projetFileRougeUi/src/app/dto');
const filesToDelete = [
    '.openapi-generator',
    'api',
    '.gitignore',
    '.openapi-generator-ignore',
    'api.module.ts',
    'configuration.ts',
    'encoder.ts',
    'git_push.sh',
    'index.ts',
    'param.ts',
    'README.md',
    'variables.ts'
];

filesToDelete.forEach(file => {
    const filePath = path.join(directory, file);
    if (fs.existsSync(filePath)) {
        fs.rmSync(filePath, { recursive: true, force: true });
        console.log(`Deleted: ${filePath}`);
    }
});
