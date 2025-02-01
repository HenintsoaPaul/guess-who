

<?php $__env->startSection('content'); ?>
    <h1>Ajouter un étudiant</h1>
    <a href="<?php echo e(route('etudiants.index')); ?>">Ajouter un étudiant</a>
    <form action="<?php echo e(route('etudiants.store')); ?>" method="POST">
        <?php echo csrf_field(); ?>
        <label>Nom : <input type="text" name="nom" value="<?php echo e(old('nom')); ?>"></label><br>
        <label>Prénom : <input type="text" name="prenom" value="<?php echo e(old('prenom')); ?>"></label><br>
        <label>Âge : <input type="number" name="age" value="<?php echo e(old('age')); ?>"></label><br>
        <button type="submit">Ajouter</button>
    </form>
<?php $__env->stopSection(); ?>

<?php echo $__env->make('layout', \Illuminate\Support\Arr::except(get_defined_vars(), ['__data', '__path']))->render(); ?><?php /**PATH C:\Users\Lenovo\Documents\Antema\devoir\s5\Mr Rojo\ProjetLaravel\example-app\resources\views/etudiants/create.blade.php ENDPATH**/ ?>