using System.Collections.Generic;
using System.Linq;

public static class ProteinTranslation
{
    private static Dictionary<string, string> ProteinNames = new()
    {
        { "AUG", "Methionine"},
        { "UUU", "Phenylalanine"},
        { "UUC", "Phenylalanine"},
        { "UUA", "Leucine"},
        { "UUG", "Leucine"},
        { "UCU", "Serine"},
        { "UCC", "Serine"},
        { "UCA", "Serine"},
        { "UCG", "Serine"},
        { "UAU", "Tyrosine"},
        { "UAC", "Tyrosine"},
        { "UGU", "Cysteine"},
        { "UGC", "Cysteine"},
        { "UGG", "Tryptophan"},
        { "UAA", "STOP"},
        { "UAG", "STOP"},
        { "UGA", "STOP"},
    };

    public static string[] Proteins(string strand) => strand
        .Chunk(3)
        .Where(sequence => ProteinNames.ContainsKey(new string(sequence)))
        .Select(sequence => ProteinNames[new string(sequence)])
        .TakeWhile(codon => codon != "STOP")
        .ToArray();
}