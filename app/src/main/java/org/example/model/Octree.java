package org.example.model;

public class Octree {
    private Parallelepiped parallelepiped;
    private Octree[] children;

    public Octree(Parallelepiped parallelepiped, Octree[] children) {
        this.parallelepiped = parallelepiped;
        this.children = children;
    }

    
}
