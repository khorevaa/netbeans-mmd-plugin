package com.igormaznitsa.ideamindmap.lang.refactoring;

import com.igormaznitsa.ideamindmap.lang.psi.PsiExtraFile;
import com.igormaznitsa.ideamindmap.utils.IdeaUtils;
import com.igormaznitsa.mindmap.model.MMapURI;
import com.igormaznitsa.mindmap.model.MindMap;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.impl.file.PsiFileImplUtil;
import com.intellij.usageView.UsageInfo;
import com.intellij.util.FileContentUtil;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URI;
import java.util.Collections;
import java.util.List;

public final class RefactoringUtils {
  private RefactoringUtils(){
  }

  public static MMapURI makeNewMMapUri(@Nonnull final Project project, @NotNull final MMapURI oldFile, @NotNull VirtualFile newFile){
    final File projectFolder = IdeaUtils.findProjectFolder(project);
    if (projectFolder == null) throw new NullPointerException("Project folder is not found for "+project);

    URI baseURI = VfsUtil.toUri(newFile);
    if (baseURI.isAbsolute()) {
      final URI projectURI = VfsUtil.toUri(projectFolder);
      baseURI = projectURI.relativize(baseURI);
    }

    return MMapURI.makeFromFilePath(projectFolder, baseURI.toString(), oldFile.getParameters());
  }

  @NotNull
  public static String replaceMMUriToNewFile(@NotNull final PsiExtraFile mindMapFile, @NotNull final MMapURI oldFile, @NotNull final MMapURI newFile) throws IOException {
    final File projectFolder = IdeaUtils.findProjectFolder(mindMapFile);
    if (projectFolder == null) throw new NullPointerException("Project folder is not found for "+mindMapFile);

    final MindMap parsedMap = new MindMap(null,new StringReader(mindMapFile.getContainingFile().getText()));
    parsedMap.replaceAllLinksToFile(projectFolder, oldFile, newFile);

    return parsedMap.write(new StringWriter(16384)).toString();
  }

  public static void reparseFile (final PsiFile file) {
    ApplicationManager.getApplication().runReadAction(new Runnable() {
      @Override public void run() {
        FileContentUtil.reparseFiles(file.getProject(), Collections.singletonList(file.getVirtualFile()), true);
      }
    });
  }
}
