

<?php $__env->startSection('content'); ?>
    <h1>Liste des étudiants</h1>
    <a href="<?php echo e(route('etudiants.create')); ?>">Ajouter un étudiant</a>
    <!-- <?php if(session('success')): ?>
        <p><?php echo e(session('success')); ?></p>
    <?php endif; ?> -->
    <ul>
        <?php $__currentLoopData = $etudiants; $__env->addLoop($__currentLoopData); foreach($__currentLoopData as $etudiant): $__env->incrementLoopIndices(); $loop = $__env->getLastLoop(); ?>
            <li>
                <?php echo e($etudiant->nom); ?> <?php echo e($etudiant->prenom); ?>, <?php echo e($etudiant->age); ?> ans
                <a href="<?php echo e(route('etudiants.edit', $etudiant)); ?>">Modifier</a>
                <form action="<?php echo e(route('etudiants.destroy', $etudiant)); ?>" method="POST" style="display:inline;">
                    <?php echo csrf_field(); ?>
                    <?php echo method_field('DELETE'); ?>
                    <button type="submit">Supprimer</button>
                </form>
            </li>
        <?php endforeach; $__env->popLoop(); $loop = $__env->getLastLoop(); ?>
    </ul>
<?php $__env->stopSection(); ?>

<?php echo $__env->make('layout', \Illuminate\Support\Arr::except(get_defined_vars(), ['__data', '__path']))->render(); ?><?php /**PATH C:\Users\Lenovo\Documents\Antema\devoir\s5\Mr Rojo\ProjetLaravel\example-app\resources\views/etudiants/listeEtudiant.blade.php ENDPATH**/ ?>