package org.dice_research.ldcbench.util.uri;


@Deprecated
public enum UriType {
    /**
     * The type of this URI is not known.
     */
    UNKNOWN,
    /**
     * The resource with this URI is dereferenceable.
     */
    DEREFERENCEABLE,
    /**
     * This URI points to a SPARQL endpoint.
     */
    SPARQL,
    /**
     * This URI points to a dump file.
     */
    DUMP, uriType;
}
