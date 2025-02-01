

<?php $__env->startSection('content'); ?>
    <h1>Modifier l'étudiant</h1>
    <form action="<?php echo e(route('etudiants.update', $etudiant)); ?>" method="POST">
        <?php echo csrf_field(); ?>
        <?php echo method_field('PUT'); ?>
        <label>Nom : <input type="text" name="nom" value="<?php echo e($etudiant->nom); ?>"></label><br>
        <label>Prénom : <input type="text" name="prenom" value="<?php echo e($etudiant->prenom); ?>"></label><br>
        <label>Âge : <input type="number" name="age" value="<?php echo e($etudiant->age); ?>"></label><br>
        <button type="submit">Mettre à jour</button>
    </form>
<?php $__env->stopSection(); ?>

<?php echo $__env->make('layout', \Illuminate\Support\Arr::except(get_defined_vars(), ['__data', '__path']))->render(); ?><?php /**PATH C:\Users\Lenovo\Documents\Antema\devoir\s5\Mr Rojo\ProjetLaravel\example-app\resources\views/etudiants/edit.blade.php ENDPATH**/ ?>